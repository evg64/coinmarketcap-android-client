package com.eugene.cmcclient.ui.model

import android.graphics.Bitmap


/**
 * Created by Eugene on 13.12.2017.
 */
data class TickerUIModel(
        val rank: String,
        val name: String,
        val price: String,
        val marketCap: String,
        val volume24h: String,
        val circulatingSupply: String,
        val symbol: String,
        val percentChange1h: String,
        val percentChange24h: String,
        val percentChange7d: String,
        val percentChange1hTextColor: Int,
        val percentChange24hTextColor: Int,
        val percentChange7dTextColor: Int,
        val logo: Bitmap?
)