package com.eugene.cmcclient.data.tickers.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Eugene on 07.12.2017.
 */
data class TickerDataModel(@SerializedName("id") val id: String,
                           @SerializedName("name") val name: String,
                           @SerializedName("symbol") val symbol: String,
                           @SerializedName("rank") val rank: Int,
                           @SerializedName("price_usd") val price: Float,
                           @SerializedName("market_cap_usd") val marketCap: Double,
                           @SerializedName("24h_volume_usd") val volume24h: Double,
                           @SerializedName("available_supply") val circulatingSupply: Double,
                           @SerializedName("percent_change_1h") val percentChange1h: Float,
                           @SerializedName("percent_change_24h") val percentChange24h: Float,
                           @SerializedName("percent_change_7d") val percentChange7d: Float
                 )