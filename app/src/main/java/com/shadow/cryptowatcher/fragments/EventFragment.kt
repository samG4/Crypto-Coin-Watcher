package com.shadow.cryptowatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.shadow.cryptowatcher.*
import com.shadow.cryptowatcher.adapter.EventListAdapter
import kotlinx.android.synthetic.main.fragment_events.*
import java.io.File

class EventFragment : Fragment(), Handler {
    private val fileDirectory: File by lazy {
        File(context?.filesDir, Directory)
    }

    private lateinit var eventDataListAdapter: EventListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    private lateinit var eventDataList: ArrayList<Data>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            fileDirectory.mkdir()
            val eventDataListContent = File(fileDirectory, EventsListFile).readFile() ?: "[]"
            eventDataList = getList(Array<Data>::class.java, eventDataListContent)
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(it)
            eventDataListAdapter = EventListAdapter(it)
            rvEvents.layoutManager = layoutManager
            if (eventDataList.isNullOrEmpty()) {
                CryptoServiceWorker(this).getEvents()
            } else {
                eventDataListAdapter.eventDataList = eventDataList
            }

            rvEvents.adapter = eventDataListAdapter

        }
    }

    override fun listSuccess(coin: List<Coin>) {
    }

    override fun compareSuccess(currencyList: JsonObject) {
    }

    override fun eventSuccess(eventsList: Events) {
        eventDataList = ArrayList(eventsList.data)
        eventDataListAdapter.eventDataList = eventDataList
        eventDataListAdapter.notifyDataSetChanged()
        File(fileDirectory, EventsListFile).writeToFile(Gson().toJson(eventDataList))
    }

    override fun handleError(throwable: Throwable) {
        Log.e("Events", throwable.message)
    }

    override fun detailsSuccess(coinDetails: CoinDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
