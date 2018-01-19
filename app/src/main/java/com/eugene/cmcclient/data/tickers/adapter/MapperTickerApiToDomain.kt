package com.eugene.cmcclient.data.tickers.adapter

import android.graphics.Bitmap
import com.eugene.cmcclient.data.tickers.model.*

/**
 * Created by Eugene on 13.01.2018.
 */
class MapperTickerApiToDomain {
    fun transform(source: List<TickerDataModel>, logos: Map<StringId, Bitmap>): List<Ticker> {
        return source.map {
            val id = StringId(it.id)
            val marketCap = Quantity(it.marketCap, Symbol.USD)
            val volume24h = Quantity(it.volume24h, Symbol.USD)
            Ticker(
                    id,
                    Name(it.name),
                    Symbol(it.symbol),
                    it.rank,
                    Price(it.price, Symbol.USD),
                    marketCap,
                    volume24h,
                    it.percentChange1h,
                    it.percentChange24h,
                    it.percentChange7d,
                    it.circulatingSupply,
                    logos[id]
            )
        }
    }
}