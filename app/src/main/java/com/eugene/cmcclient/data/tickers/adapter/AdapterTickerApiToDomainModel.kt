package com.eugene.cmcclient.data.tickers.adapter

import android.graphics.Bitmap
import com.eugene.cmcclient.data.tickers.model.*

/**
 * Created by Eugene on 13.01.2018.
 */
class AdapterTickerApiToDomainModel {
    fun transform(source: List<TickerDataModel>, logos: Map<StringId, Bitmap>): List<Ticker> {
        val usd = Symbol("USD")
//        var bitmap: Bitmap? = null
//        if (!logos.isEmpty()) {
//            for (bmp in logos.values) {
//                bitmap = bmp
//                break
//            }
//        }
        return source.map {
            val id = StringId(it.id)
            val marketCap = Quantity(it.marketCap, usd)
            val volume24h = Quantity(it.volume24h, usd)
            Ticker(
                    id,
                    Name(it.name),
                    Symbol(it.symbol),
                    it.rank,
                    Price(it.price, usd),
                    marketCap,
                    volume24h,
                    it.percentChange1h,
                    it.percentChange24h,
                    it.percentChange7d,
                    it.circulatingSupply,
//                    bitmap
                    logos[id]
            )
        }
    }
}