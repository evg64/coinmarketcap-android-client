package com.eugene.cmcclient.ui.model

import com.eugene.cmcclient.data.tickers.TickerDataModel

/**
 * Created by Eugene on 13.12.2017.
 */
data class TickerModel(
        val rank: Int,
        val name: String,
        val price: Float,
        val marketCap: Double,
        val volume24h: Double,
        val circulatingSupply: Double,
        val symbol: String,
        val percentChange24h: Float
                      ) {
    companion object {
        fun from(tickers: List<TickerDataModel>): List<TickerModel> = tickers.map { from(it) }

        fun from(ticker: TickerDataModel) = TickerModel(ticker.rank,
                                                        ticker.name,
                                                        ticker.price,
                                                        ticker.marketCap,
                                                        ticker.volume24h,
                                                        ticker.circulatingSupply,
                                                        ticker.symbol,
                                                        ticker.percentChange24h)
    }
}