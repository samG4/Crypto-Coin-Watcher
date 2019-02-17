package com.shadow.cryptowatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.shadow.cryptowatcher.*
import com.shadow.cryptowatcher.adapter.CoinValueListAdapter
import kotlinx.android.synthetic.main.fragment_watcher.*
import org.json.JSONObject
import java.io.File


class WatcherFragment : Fragment(), Handler {

    private val fileDirectory: File by lazy {
        File(context?.filesDir, Directory)
    }
    private lateinit var cryptoWorker: CryptoServiceWorker
    private var ids = ""
    private var currencies = "inr,usd"
    private var coinListAdapter: CoinValueListAdapter? = null
    private var coinsValueTrackList: ArrayList<Watcher>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(it)
            coinListAdapter = CoinValueListAdapter(it)

            fileDirectory.mkdir()
            val coinWatchListContent = File(fileDirectory, CoinWatchListFile).readFile() ?: "[]"
            val coinWatcherList = getList(Array<Watcher>::class.java, coinWatchListContent)
            if (coinWatcherList.isNotEmpty()) {
                val idslist = ArrayList<String>()
                coinWatcherList.forEach {
                    idslist.add(it.id)
                }
                ids = TextUtils.join(",", idslist)
                cryptoWorker = CryptoServiceWorker(this)
                cryptoWorker.getCurrentPrice(ids, currencies)
            }
            rvCoinMarValue.layoutManager = layoutManager
            rvCoinMarValue.adapter = coinListAdapter
        }
        swipe_view.setOnRefreshListener {
            cryptoWorker.disposable.dispose()
            cryptoWorker.getCurrentPrice(ids, currencies)
        }
    }

    override fun onResume() {
        if (cryptoWorker.disposable.isDisposed) {
            cryptoWorker.getCurrentPrice(ids, currencies)
        }
        super.onResume()
    }

    override fun onStop() {
        cryptoWorker.disposable.dispose()
        super.onStop()
    }

    override fun listSuccess(coin: List<Coin>) {
    }

    override fun eventSuccess(eventsList: Events) {
    }

    override fun compareSuccess(currencyList: JsonObject) {
        val gson = Gson()
        coinsValueTrackList = ArrayList()
        val content = JSONObject(gson.toJson(currencyList))
        val list = content.keys()
        while (list.hasNext()) {
            val currentId = list.next() as String
            val currentCurrency = content.getJSONObject(currentId)
            val currency: Currency = gson.fromJson(currentCurrency.toString(), Currency::class.java)
            coinsValueTrackList?.add(Watcher(currentId, currency))
        }
        activity?.runOnUiThread {
            coinsValueTrackList?.let {
                coinListAdapter?.watchList = it
                coinListAdapter?.notifyDataSetChanged()
            }
            swipe_view.isRefreshing = false
        }
    }

    override fun handleError(throwable: Throwable) {
        Log.e("Watcher", throwable.message)
    }

    override fun detailsSuccess(coinDetails: CoinDetails) {
    }
}
