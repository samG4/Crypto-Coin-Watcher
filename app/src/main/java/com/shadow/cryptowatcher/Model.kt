package com.shadow.cryptowatcher
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Coin(
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("symbol")
    var symbol: String
):Parcelable

@Parcelize
data class Watcher(
    var id : String,
    var currency : Currency
):Parcelable

@Parcelize
data class Currency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
):Parcelable

data class Events(
    @SerializedName("count")
    var count: Int,
    @SerializedName("data")
    var data: List<Data>,
    @SerializedName("page")
    var page: Int
)

@Parcelize
data class Data(
    @SerializedName("address")
    var address: String,
    @SerializedName("screenshot")
    var screenshot: String,
    @SerializedName("start_date")
    var startDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("venue")
    var venue: String
): Parcelable


data class CoinDetails(
    @SerializedName("description")
    var description: Description,
    @SerializedName("id")
    var id: String,
    @SerializedName("image")
    var image: Image,
    @SerializedName("links")
    var links: Links,
    @SerializedName("market_cap_rank")
    var marketCapRank: Int,
    @SerializedName("market_data")
    var marketData: MarketData,
    @SerializedName("name")
    var name: String,
    @SerializedName("symbol")
    var symbol: String
)

data class Links(
    @SerializedName("blockchain_site")
    var blockchainSite: List<String>,
    @SerializedName("homepage")
    var homepage: List<String>,
    @SerializedName("official_forum_url")
    var officialForumUrl: List<String>
)

data class MarketData(
    @SerializedName("current_price")
    var currentPrice: CurrentPrice,
    @SerializedName("high_24h")
    var high24h: High24h,
    @SerializedName("low_24h")
    var low24h: Low24h,
    @SerializedName("market_cap")
    var marketCap: MarketCap,
    @SerializedName("market_cap_change_24h")
    var marketCapChange24h: String,
    @SerializedName("market_cap_change_24h_in_currency")
    var marketCapChange24hInCurrency: MarketCapChange24hInCurrency,
    @SerializedName("market_cap_change_percentage_24h")
    var marketCapChangePercentage24h: String,
    @SerializedName("market_cap_change_percentage_24h_in_currency")
    var marketCapChangePercentage24hInCurrency: MarketCapChangePercentage24hInCurrency,
    @SerializedName("market_cap_rank")
    var marketCapRank: Int,
    @SerializedName("price_change_24h")
    var priceChange24h: String,
    @SerializedName("price_change_24h_in_currency")
    var priceChange24hInCurrency: PriceChange24hInCurrency,
    @SerializedName("price_change_percentage_14d")
    var priceChangePercentage14d: String,
    @SerializedName("price_change_percentage_14d_in_currency")
    var priceChangePercentage14dInCurrency: PriceChangePercentage14dInCurrency,
    @SerializedName("price_change_percentage_1h_in_currency")
    var priceChangePercentage1hInCurrency: PriceChangePercentage1hInCurrency,
    @SerializedName("price_change_percentage_1y")
    var priceChangePercentage1y: String,
    @SerializedName("price_change_percentage_1y_in_currency")
    var priceChangePercentage1yInCurrency: PriceChangePercentage1yInCurrency,
    @SerializedName("price_change_percentage_200d")
    var priceChangePercentage200d: String,
    @SerializedName("price_change_percentage_200d_in_currency")
    var priceChangePercentage200dInCurrency: PriceChangePercentage200dInCurrency,
    @SerializedName("price_change_percentage_24h")
    var priceChangePercentage24h: String,
    @SerializedName("price_change_percentage_24h_in_currency")
    var priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency,
    @SerializedName("price_change_percentage_30d")
    var priceChangePercentage30d: String,
    @SerializedName("price_change_percentage_30d_in_currency")
    var priceChangePercentage30dInCurrency: PriceChangePercentage30dInCurrency,
    @SerializedName("price_change_percentage_60d")
    var priceChangePercentage60d: String,
    @SerializedName("price_change_percentage_60d_in_currency")
    var priceChangePercentage60dInCurrency: PriceChangePercentage60dInCurrency,
    @SerializedName("price_change_percentage_7d")
    var priceChangePercentage7d: String,
    @SerializedName("price_change_percentage_7d_in_currency")
    var priceChangePercentage7dInCurrency: PriceChangePercentage7dInCurrency,
    @SerializedName("total_supply")
    var totalSupply: Int,
    @SerializedName("total_volume")
    var totalVolume: TotalVolume
)

data class PriceChangePercentage1yInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class MarketCapChangePercentage24hInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage200dInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class High24h(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage1hInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class MarketCapChange24hInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage7dInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class MarketCap(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage60dInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage14dInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class CurrentPrice(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChange24hInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage24hInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class PriceChangePercentage30dInCurrency(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class TotalVolume(
    @SerializedName("btc")
    var btc: Double
)

data class Low24h(
    @SerializedName("inr")
    var inr: Double,
    @SerializedName("usd")
    var usd: Double
)

data class Image(
    @SerializedName("large")
    var large: String,
    @SerializedName("small")
    var small: String,
    @SerializedName("thumb")
    var thumb: String
)

data class Description(
    @SerializedName("en")
    var en: String
)