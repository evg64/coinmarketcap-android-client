package com.eugene.cmcclient.ui.model

import android.databinding.Bindable
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.eugene.cmcclient.App
import com.eugene.cmcclient.data.tickers.model.Ticker


/**
 * Created by Eugene on 13.12.2017.
 */
data class TickerUIModel(
        val rank: Int,
        val name: String,
        val price: Float,
        val marketCap: Double,
        val volume24h: Double,
        val circulatingSupply: Double,
        val symbol: String,
        val percentChange24h: Float,
        val logo: Bitmap?
) {
    companion object {
        fun from(tickers: List<Ticker>): List<TickerUIModel> = tickers.map { from(it) }

        fun from(ticker: Ticker) = TickerUIModel(ticker.rank,
                                                 ticker.name.value,
                                                 ticker.price.value,
                                                 ticker.marketCap.value,
                                                 ticker.volume24h.value,
                                                 ticker.circulatingSupply,
                                                 ticker.symbol.name,
                                                 ticker.percentChange24h,
                                                 ticker.logo)
    }
}