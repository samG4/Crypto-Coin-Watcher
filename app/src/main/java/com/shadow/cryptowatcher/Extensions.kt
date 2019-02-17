package com.shadow.cryptowatcher

import android.view.View
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream

const val CoinListFile = "COIN_LIST"
const val CoinWatchListFile = "COIN_WATCHER"
const val EventsListFile = "EVENTS"
const val Key_ID = "Id"
const val Directory = "CryptoWatcher"



fun View.toggleVisibility(view: View) {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
        view.visibility = View.VISIBLE
    } else {
        this.visibility = View.VISIBLE
        view.visibility = View.GONE
    }
}

fun File.writeToFile(fileContents: String) {
    Observable.fromCallable<Any> {
        this.writeText(fileContents)
        0
    }.subscribeOn(Schedulers.io()).subscribe()
}

fun File.readFile(): String? {
    this.let {
        if (it.exists())
            return FileInputStream(it).bufferedReader().use { it.readText() }
    }
    return null
}

fun <T> getList(clazz: Class<Array<T>>, json: String): ArrayList<T> {
    val jsonToObject = Gson().fromJson(json, clazz)
    val list = ArrayList<T>()
    jsonToObject.forEach {
        list.add(it)
    }
    return list
}

