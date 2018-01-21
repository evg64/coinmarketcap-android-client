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

    private fun map(ticker: Ticker) = TickerUIModel(
            rank = "${ticker.rank}",
            name = ticker.name.value,
            price = priceFormatter.format(ticker.price),
            marketCap = marketCapFormatter.format(ticker.marketCap),
            volume24h = volumeFormatter.format(ticker.volume24h),
            circulatingSupply = circulatingSupplyFormatter.format(ticker.circulatingSupply),
            symbol = ticker.symbol.name,
            percentChange1h = percentFormatter.formatTwice(R.string.percent_1h, ticker.percentChange1h),
            percentChange24h = percentFormatter.formatTwice(R.string.percent_24h, ticker.percentChange24h),
            percentChange7d = percentFormatter.formatTwice(R.string.percent_7d, ticker.percentChange7d),
            percentChange1hTextColor = selectColorByPercent(ticker.percentChange1h),
            percentChange24hTextColor = selectColorByPercent(ticker.percentChange24h),
            percentChange7dTextColor = selectColorByPercent(ticker.percentChange7d),
            logo = ticker.logo
    )

    private fun selectColorByPercent(percent: Float): Int {
        return when {
            percent > 0 -> colorPositive
            percent < 0 -> colorNegative
            else -> colorNeutral
        }
    }
}