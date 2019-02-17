package com.shadow.cryptowatcher

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit


interface CryptoCoinApi {
    @GET("coins/list")
    fun getCoinsList(): Observable<List<Coin>>

    @GET("simple/price")
    fun getCurrentPrice(@Query("ids") ids: String, @Query("vs_currencies") currencies: String): Observable<JsonObject>

    @GET("simple/price")
    fun getCurrentPriceRepeatedly(@Query("ids") ids: String, @Query("vs_currencies") currencies: String): Call<JsonObject>


    @GET("events")
    fun getEventsByDate(): Observable<Events>

    @GET("coins/{id}")
    fun getCoinDetails(@Path("id") id: String): Observable<CoinDetails>
}

class CryptoServiceWorker(val handler: Handler) {

    companion object {
        const val cryptoApi = "https://api.coingecko.com/api/v3/"
    }

    val requestInterface = Retrofit.Builder()
        .baseUrl(cryptoApi)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(CryptoCoinApi::class.java)

    fun getCoinList() {
        CompositeDisposable()
            .add(
                requestInterface.getCoinsList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(handler::listSuccess, handler::handleError)
            )
    }

    lateinit var disposable: Disposable
    fun getCurrentPrice(ids: String, currencies: String) {
        try {
            disposable = Observable.fromCallable {
                val call = requestInterface.getCurrentPriceRepeatedly(ids, currencies).execute()
                if (call.isSuccessful) {
                    call.body()?.let {
                        handler.compareSuccess(it)
                    }

                }
            }
                .delay(1, TimeUnit.MINUTES)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }catch (e: InterruptedIOException){
            disposable.dispose()
        }
    }

    fun getEvents() {
        CompositeDisposable()
            .add(
                requestInterface.getEventsByDate()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(handler::eventSuccess, handler::handleError)
            )
    }

    fun getCoinDetails(id: String) {
        CompositeDisposable()
            .add(
                requestInterface.getCoinDetails(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(handler::detailsSuccess, handler::handleError)
            )
    }

}

interface Handler {
    fun listSuccess(coin: List<Coin>)
    fun compareSuccess(currencyList: JsonObject)
    fun eventSuccess(eventsList: Events)
    fun detailsSuccess(coinDetails: CoinDetails)
    fun handleError(throwable: Throwable)
}