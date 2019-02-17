package com.shadow.cryptowatcher.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadow.cryptowatcher.Data
import com.shadow.cryptowatcher.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_item.view.*

class EventListAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var eventDataList= ArrayList<Data>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return EventViewHolder(
            LayoutInflater.from(context).inflate(R.layout.event_item, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return eventDataList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as EventViewHolder).bindView(eventDataList[p1])
    }

    inner class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imgThumbnail = itemView.imgThumbnail
        private val tvEventName = itemView.tvEventName
        private val tvEventType = itemView.tvEventType
        private val tvDate = itemView.tvDayDate
        private val tvAddress = itemView.tvAddress

        fun bindView(data: Data){
            Picasso.get().load(data.screenshot).into(imgThumbnail)
            tvEventName.text = data.title
            tvEventType.text = data.type
            tvDate.text = data.startDate
            tvAddress.text = data.address
        }
    }

}