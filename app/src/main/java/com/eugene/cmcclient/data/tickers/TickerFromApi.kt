package com.eugene.cmcclient.data.tickers

import com.google.gson.annotations.SerializedName

/**
 * Created by Eugene on 07.12.2017.
 */
data class TickerFromApi(@SerializedName("id") val id: String,
                         @SerializedName("name") val name: String,
                         @SerializedName("symbol") val symbol: String,
                         @SerializedName("rank") val rank: Int,
                         @SerializedName("price_usd") val price: Float,
                         @SerializedName("market_cap_usd") val marketCap: Double,
                         @SerializedName("24h_volume_usd") val volume24h: Double,
                         @SerializedName("available_supply") val circulatingSupply: Double,
                         @SerializedName("percent_change_24h") val percentChange24h: Float
                 )