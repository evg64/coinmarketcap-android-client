package com.eugene.cmcclient.data.tickers.cache

import com.eugene.cmcclient.data.tickers.TickerFromApi

/**
 * Created by Eugene on 09.01.2018.
 */
class InMemoryCacheTickers : CacheTickers {
    override fun reset() {
        cache.clear()
    }

    private val cache: MutableList<TickerFromApi> = ArrayList()

    override fun isExpired(): Boolean {
        return false
    }

    override fun put(ticker: TickerFromApi, atIndex: Int) {
        val size = cache.size
        if (atIndex > size) {
            throw IllegalArgumentException("Cache size is ${cache.size}, cannot add elements at $atIndex")
        }
        if (atIndex == size) {
            cache.add(ticker)
        } else if (atIndex < size) {
            cache[atIndex] = ticker
        }
    }

    override fun put(tickers: List<TickerFromApi>, atIndex: Int) {
        if (atIndex == cache.size) {
            cache.addAll(tickers)
        } else {
            tickers.forEachIndexed({ index, ticker -> put(ticker, index) })
        }
    }

    override fun get(from: Int, count: Int): List<TickerFromApi>? {
        return if (cache.size < from + count) {
            null
        } else {
            cache.subList(from, count)
        }
    }
}