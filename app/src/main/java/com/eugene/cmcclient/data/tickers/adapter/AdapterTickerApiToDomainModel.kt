package com.eugene.cmcclient.data.tickers.adapter

import android.graphics.Bitmap
import com.eugene.cmcclient.data.tickers.model.*
import java.net.URI

/**
 * Created by Eugene on 13.01.2018.
 */
class AdapterTickerApiToDomainModel {
    fun transform(source: List<TickerDataModel>, logos: Map<Name, Bitmap>): List<Ticker> {
        val usd = Symbol("USD")
        var bitmap: Bitmap? = null
        if (!logos.isEmpty()) {
            for (bmp in logos.values) {
                bitmap = bmp
                break
            }
        }
        return source.map {
            val name = Name(it.name)
            val symbol = Symbol(it.symbol)
            val marketCap = Quantity(it.marketCap, usd)
            val volume24h = Quantity(it.volume24h, usd)
            Ticker(
                    it.id,
                    name,
                    symbol,
                    it.rank,
                    Price(it.price, usd),
                    marketCap,
                    volume24h,
                    it.percentChange24h,
                    it.circulatingSupply,
                    bitmap
//                    logos[name]
            )
        }
    }
}