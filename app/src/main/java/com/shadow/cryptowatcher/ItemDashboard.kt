package com.shadow.cryptowatcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_dashboard.*


class ItemDashboard : AppCompatActivity(), Handler {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_dashboard)
        val id = intent.getStringExtra(Key_ID)
        CryptoServiceWorker(this).getCoinDetails(id)
    }

    override fun listSuccess(coin: List<Coin>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compareSuccess(currencyList: JsonObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun eventSuccess(eventsList: Events) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detailsSuccess(coinDetails: CoinDetails) {
        loadView(coinDetails)
    }

    private fun loadView(coinDetails: CoinDetails) {
        Picasso.get().load(coinDetails.image.large).into(imgSymbol)
        tvName.text = (coinDetails.name + "( " + coinDetails.symbol + " )")
        tvCurrentValue.text = ("$" + coinDetails.marketData.currentPrice.usd.toString())
        val home = coinDetails.links.homepage.first { it.isNotEmpty() }.apply {
            webLabel.visibility = View.VISIBLE
        }
        tvHomePage.text = home
        val blockChain = coinDetails.links.blockchainSite.first { it.isNotEmpty() }.apply {
            expLabel.visibility = View.VISIBLE
        }
        tvBlockChainSites.text = blockChain
        val official = coinDetails.links.officialForumUrl.first { it.isNotEmpty() }.apply {
            commLabel.visibility = View.VISIBLE
        }
        tvOfficialForum.text = official
        tvOverview.text = coinDetails.description.en
        tvOverview.movementMethod = ScrollingMovementMethod()
        tvOverViewHeading.visibility = View.VISIBLE
    }

    override fun handleError(throwable: Throwable) {
        Log.e("ItemDB", throwable.message)
    }
}
