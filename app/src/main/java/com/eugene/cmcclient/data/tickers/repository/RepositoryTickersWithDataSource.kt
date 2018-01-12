package com.eugene.cmcclient.data.tickers.repository

import com.eugene.cmcclient.data.tickers.Ticker
import com.eugene.cmcclient.data.tickers.datasource.DataSourceTickers
import io.reactivex.Observable

/**
 * Created by Eugene on 09.12.2017.
 */
open class RepositoryTickersWithDataSource(private val source: DataSourceTickers) : RepositoryTickers {

    override fun reset() {
        source.reset()
    }

    override fun getTickers(from: Int, limit: Int): Observable<List<Ticker>> {
        return source.getTickers(from, limit).retry(2)
    }

}