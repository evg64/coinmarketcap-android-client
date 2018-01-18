package com.eugene.cmcclient.data.tickers.model

import android.graphics.Bitmap

/**
 * Created by Eugene on 13.01.2018.
 */
data class Ticker(
        val id: StringId,
        val name: Name,
        val symbol: Symbol,
        val rank: Int,
        val price: Price,
        val marketCap: Quantity,
        val volume24h: Quantity,
        val percentChange1h: Float,
        val percentChange24h: Float,
        val percentChange7d: Float,
        val circulatingSupply: Double,
        val logo: Bitmap?
)