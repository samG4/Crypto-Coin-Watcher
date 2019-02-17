package com.shadow.cryptowatcher.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadow.cryptowatcher.*
import kotlinx.android.synthetic.main.coin_item.view.*
import java.io.File

class CoinListAdapter(private val context: Context, private val coinWatcherHandler: CoinWatcherHandler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var coinList = ArrayList<Coin>()
    var watchList= ArrayList<Watcher>()
    internal val coinIdList = ArrayList<String>()

    init {
        val directory = File(context.filesDir, Directory)
        directory.mkdir()
        watchList = getList(
            Array<Watcher>::class.java,
            File(directory, CoinWatchListFile).readFile() ?: "[]"
        )
        if (!watchList.isNullOrEmpty()) {
            watchList.forEach {
                coinIdList.add(it.id)
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return CoinViewHolder(
            LayoutInflater.from(context).inflate(R.layout.coin_item, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as CoinViewHolder).bindView(coinList[p1])
    }

    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvCoinName = itemView.tvCoinName
        private val imgAdd = itemView.imgAdd
        private val imgSub = itemView.imgRemove
        lateinit var coin: Coin

        init {
            imgAdd.setOnClickListener(this)
            imgSub.setOnClickListener(this)
            tvCoinName.setOnClickListener(this)
        }

        fun bindView(coin: Coin) {
            this.coin = coin
            tvCoinName.text = (coin.name + " (" + coin.symbol + ") ").toUpperCase()
            val isAdded = coinIdList.indexOf(coin.id)
            if (isAdded != -1) {
                imgAdd.visibility  = View.GONE
                imgSub.visibility = View.VISIBLE
            }
            else{
                imgAdd.visibility  = View.VISIBLE
                imgSub.visibility = View.GONE
            }
        }

        override fun onClick(p0: View?) {
            when (p0) {
                imgAdd -> {
                    imgAdd.toggleVisibility(imgSub)
                    coinWatcherHandler.addToWatchList(coin)
                }
                imgSub -> {
                    imgSub.toggleVisibility(imgAdd)
                    coinWatcherHandler.removeFromWatchList(coin)
                }
                tvCoinName -> {
                    coinWatcherHandler.coinTapped(coin)
                }
            }
        }

    }

    interface CoinWatcherHandler {
        fun addToWatchList(coin: Coin)
        fun removeFromWatchList(coin: Coin)
        fun coinTapped(coin: Coin)
    }
}
