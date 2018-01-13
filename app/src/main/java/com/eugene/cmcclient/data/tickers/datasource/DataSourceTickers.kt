package com.eugene.cmcclient.data.tickers.datasource

import com.eugene.cmcclient.data.tickers.TickerFromApi
import io.reactivex.Observable

/**
 * Created by Eugene on 10.01.2018.
 */
interface DataSourceTickers {
    fun getTickers(from: Int, limit: Int) : Observable<List<TickerFromApi>>
    fun reset()
}