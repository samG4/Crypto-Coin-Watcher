package com.shadow.cryptowatcher.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.shadow.cryptowatcher.*
import com.shadow.cryptowatcher.adapter.CoinListAdapter
import kotlinx.android.synthetic.main.fragment_coin_list.*
import java.io.File


class HomeFragment : Fragment(), Handler, CoinListAdapter.CoinWatcherHandler {
    private var coinList = ArrayList<Coin>()


    private var coinWatcherList = ArrayList<Watcher>()
    private lateinit var coinListAdapter: CoinListAdapter
    private val fileDirectory: File by lazy {
        File(context?.filesDir, Directory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coin_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(it)
            coinListAdapter = CoinListAdapter(it, this)
            rvCoinList.layoutManager = layoutManager

            fileDirectory.mkdir()
            val coinWatchListContent = File(fileDirectory, CoinWatchListFile).readFile() ?: "[]"
            val coinListContent = File(fileDirectory, CoinListFile).readFile() ?: "[]"
            coinList = getList(Array<Coin>::class.java, coinListContent)
            coinWatcherList = getList(Array<Watcher>::class.java, coinWatchListContent)
        }
        if (coinList.isNullOrEmpty()) {
            CryptoServiceWorker(this).getCoinList()
        } else {
            coinListAdapter.coinList = coinList
            coinListAdapter.notifyDataSetChanged()
            rvCoinList.adapter = coinListAdapter
        }
    }

    override fun onStop() {
        File(fileDirectory, CoinWatchListFile).writeToFile(Gson().toJson(coinWatcherList))
        super.onStop()
    }

    override fun listSuccess(coin: List<Coin>) {
        coinList = ArrayList(coin)
        coinListAdapter.coinList = coinList
        coinListAdapter.notifyDataSetChanged()
        File(fileDirectory, CoinListFile).writeToFile(Gson().toJson(coinList))
    }

    override fun compareSuccess(currencyList: JsonObject) {
    }

    override fun eventSuccess(eventsList: Events) {
    }

    override fun handleError(throwable: Throwable) {
    }

    override fun detailsSuccess(coinDetails: CoinDetails) {
    }

    override fun addToWatchList(coin: Coin) {
        coinWatcherList.add(Watcher(coin.id, Currency(0.0, 0.0)))
    }

    override fun removeFromWatchList(coin: Coin) {
        val item = coinWatcherList.first { it.id == coin.id }
        coinWatcherList.remove(item)
    }

    override fun coinTapped(coin: Coin) {
        val intent = Intent(activity, ItemDashboard::class.java)
        intent.putExtra(Key_ID, coin.id)
        startActivity(intent)
    }

}
