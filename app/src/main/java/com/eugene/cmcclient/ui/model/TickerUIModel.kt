package com.eugene.cmcclient.ui.model

import android.graphics.drawable.Drawable
import android.text.Spanned


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
        // market cap \n volume24h \n circulating supply
        val capVolumeCirculatingSupply: Spanned,
        val symbol: String,
        val percentChange1h: String,
        val percentChange24h: String,
        val percentChange7d: String,
        val percentChange1hTextColor: Int,
        val percentChange24hTextColor: Int,
        val percentChange7dTextColor: Int,
        // 1h, 24h, 7d percent changes
        val percentChanges: Spanned,
        // drawable is more preferable than bitmap since it is faster to
        // be set inside imageview (setImageDrawable is called inside setImageBitmap)
        val logo: Drawable?
)