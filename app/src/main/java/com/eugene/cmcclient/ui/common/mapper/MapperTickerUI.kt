package com.eugene.cmcclient.ui.common.mapper

import com.eugene.cmcclient.R
import com.eugene.cmcclient.data.tickers.model.Ticker
import com.eugene.cmcclient.di.Injector
import com.eugene.cmcclient.ui.common.formatters.*
import com.eugene.cmcclient.ui.model.TickerUIModel

/**
 * Created by Eugene on 18.01.2018.
 */
class MapperTickerUI {

    fun map(tickers: List<Ticker>): List<TickerUIModel> = tickers.map { map(it) }

    private val priceFormatter = PriceFormatter()
    private val marketCapFormatter = MarketCapFormatter()
    private val volumeFormatter = VolumeFormatter()
    private val circulatingSupplyFormatter = CirculatingSupplyFormatter()
    private val percentFormatter = FloatPercentFormatter()
    private val colorPositive: Int
    private val colorNegative: Int
    private val colorNeutral: Int

    init {
        val res = Injector.componentApp.getRes()
        colorPositive = res.getColor(R.color.green_positive)
        colorNegative = res.getColor(R.color.red_negavite)
        colorNeutral = res.getColor(R.color.black_neutral)
    }

    private fun map(ticker: Ticker): TickerUIModel {
        return TickerUIModel("${ticker.rank}",
                             ticker.name.value,
                             priceFormatter.format(ticker.price),
                             marketCapFormatter.format(ticker.marketCap),
                             volumeFormatter.format(ticker.volume24h),
                             circulatingSupplyFormatter.format(ticker.circulatingSupply),
                             ticker.symbol.name,
                             percentFormatter.format(ticker.percentChange1h),
                             percentFormatter.format(ticker.percentChange24h),
                             percentFormatter.format(ticker.percentChange7d),
                             selectColorByPercent(ticker.percentChange1h),
                             selectColorByPercent(ticker.percentChange24h),
                             selectColorByPercent(ticker.percentChange7d),
                             ticker.logo)
    }

    private fun selectColorByPercent(percent: Float): Int {
        return when {
            percent > 0 -> colorPositive
            percent < 0 -> colorNegative
            else -> colorNeutral
        }
    }
}