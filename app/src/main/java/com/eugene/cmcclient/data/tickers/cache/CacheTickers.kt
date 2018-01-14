package com.eugene.cmcclient.data.tickers.cache

import com.eugene.cmcclient.data.tickers.model.TickerDataModel

/**
 * Created by Eugene on 09.01.2018.
 */
interface CacheTickers {
    fun isExpired() : Boolean

    /**
     * overwrites previously cached element or inserts a new one
     * @param atIndex if larger than current size of cache, throws [IllegalArgumentException]
     */
    fun put(ticker: TickerDataModel, atIndex: Int)

    /**
     * overwrites previously cached elements or inserts new ones
     * @param atIndex if larger than current size of cache, throws [IllegalArgumentException]
     */
    fun put(tickers: List<TickerDataModel>, atIndex: Int)

    /**
     * if at least 1 element is not cached, returns null
     */
    fun get(from: Int, count: Int): List<TickerDataModel>?

    fun reset()

}