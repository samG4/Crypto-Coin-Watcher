package com.shadow.cryptowatcher.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadow.cryptowatcher.R
import com.shadow.cryptowatcher.Watcher
import kotlinx.android.synthetic.main.coin_value_item.view.*

class CoinValueListAdapter(val context: Context)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var watchList = ArrayList<Watcher>()



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return CoinValueViewHolder(
            LayoutInflater.from(context).inflate(R.layout.coin_value_item, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return watchList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as CoinValueViewHolder).bindView(watchList[p1])
    }

    inner class CoinValueViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvName = itemView.tvCoinName
        private val tvInr = itemView.tvInr
        private val tvUsd = itemView.tvUsd

        fun bindView(watcher: Watcher){
            tvName.text = watcher.id
            tvInr.text = watcher.currency.inr.toString()
            tvUsd.text = watcher.currency.usd.toString()
        }
    }
}