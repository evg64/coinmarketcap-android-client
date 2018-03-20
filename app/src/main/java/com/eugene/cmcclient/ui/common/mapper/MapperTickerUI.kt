package com.eugene.cmcclient.ui.common.mapper

import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.text.Html
import android.text.Spanned
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
            symbol = ticker.symbol.name,
            capVolumeCirculatingSupply = getCapVolumeCirculatingSupplySpannable(ticker),
            percentChanges = getPercentChanges(ticker),
            logo = if(ticker.logo == null) null else BitmapDrawable(Injector.componentApp.getRes(), ticker.logo),               marketCap = marketCapFormatter.format(ticker.marketCap),
            volume24h = volumeFormatter.format(ticker.volume24h),
            circulatingSupply = circulatingSupplyFormatter.format(ticker.circulatingSupply),
            percentChange1h = percentFormatter.formatTwice(R.string.percent_1h, ticker.percentChange1h),
            percentChange24h = percentFormatter.formatTwice(R.string.percent_24h, ticker.percentChange24h),
            percentChange7d = percentFormatter.formatTwice(R.string.percent_7d, ticker.percentChange7d),
            percentChange1hTextColor = selectColorByPercent(ticker.percentChange1h),
            percentChange24hTextColor = selectColorByPercent(ticker.percentChange24h),
            percentChange7dTextColor = selectColorByPercent(ticker.percentChange7d)
    )

    companion object {
        private const val HTML_FORMAT_PERCENT_CHANGES = "<font color=\"%s\">%s</font><br><br>" +
                "<font color=\"%s\">%s</font><br><br>" +
                "<font color=\"%s\">%s</font>"
        private const val HTML_FORMAT_CAP_VOLUME_SUPPLY = "%s<br><br>%s<br><br>%s"
    }

    private fun getCapVolumeCirculatingSupplySpannable(ticker: Ticker): Spanned {
        val html = String.format(HTML_FORMAT_CAP_VOLUME_SUPPLY,
                                 marketCapFormatter.format(ticker.marketCap),
                                 volumeFormatter.format(ticker.volume24h),
                                 circulatingSupplyFormatter.format(ticker.circulatingSupply)
        )
        return getHtml(html)
    }

    private fun getHtml(html: String): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, 0)
    } else {
        Html.fromHtml(html)
    }

    private fun getPercentChanges(ticker: Ticker): Spanned {
        val html =  String.format(HTML_FORMAT_PERCENT_CHANGES,
                                  selectStringColorByPercent(ticker.percentChange1h),
                                  percentFormatter.formatTwice(R.string.percent_1h, ticker.percentChange1h),
                                  selectStringColorByPercent(ticker.percentChange24h),
                                  percentFormatter.formatTwice(R.string.percent_24h, ticker.percentChange24h),
                                  selectStringColorByPercent(ticker.percentChange7d),
                                  percentFormatter.formatTwice(R.string.percent_7d, ticker.percentChange7d)
        )
        return getHtml(html)
    }

//            logo = ticker.logo

    private fun selectColorByPercent(percent: Float): Int {
        return when {
            percent > 0 -> colorPositive
            percent < 0 -> colorNegative
            else -> colorNeutral
        }
    }

    private fun selectStringColorByPercent(percent: Float): String {
        val color =  selectColorByPercent(percent)
        return "#${Integer.toString(color, 16)}"
    }
}