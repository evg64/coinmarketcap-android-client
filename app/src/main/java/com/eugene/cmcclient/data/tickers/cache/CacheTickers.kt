package com.eugene.cmcclient.data.tickers.cache

import com.eugene.cmcclient.data.tickers.TickerFromApi

/**
 * Created by Eugene on 09.01.2018.
 */
interface CacheTickers {
    fun isExpired() : Boolean

    /**
     * overwrites previously cached element or inserts a new one
     * @param atIndex if larger than current size of cache, throws [IllegalArgumentException]
     */
    fun put(ticker: TickerFromApi, atIndex: Int)

    /**
     * overwrites previously cached elements or inserts new ones
     * @param atIndex if larger than current size of cache, throws [IllegalArgumentException]
     */
    fun put(tickers: List<TickerFromApi>, atIndex: Int)

    /**
     * if at least 1 element is not cached, returns null
     */
    fun get(from: Int, count: Int): List<TickerFromApi>?

    fun reset()

}