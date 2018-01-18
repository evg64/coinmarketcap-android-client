package com.eugene.cmcclient.ui.model

import android.graphics.Bitmap
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
        val percentChange1h: Float,
        val percentChange24h: Float,
        val percentChange7d: Float,
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
                                                 ticker.percentChange1h,
                                                 ticker.percentChange24h,
                                                 ticker.percentChange7d,
                                                 ticker.logo)
    }
}