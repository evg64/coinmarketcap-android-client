package com.eugene.cmcclient.data.tickers.datasource

import com.eugene.cmcclient.data.Backend
import com.eugene.cmcclient.data.tickers.Ticker
import io.reactivex.Observable

/**
 * Created by Eugene on 10.01.2018.
 */
class DataSourceTickersNetwork(private val backend: Backend) : DataSourceTickers {
    override fun reset() {}

    override fun getTickers(from: Int, limit: Int): Observable<List<Ticker>> {
        return backend.getTickers(from, limit)
    }

}