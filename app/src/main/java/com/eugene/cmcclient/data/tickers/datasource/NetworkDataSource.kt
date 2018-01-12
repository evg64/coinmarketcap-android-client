package com.eugene.cmcclient.data.tickers.datasource

import com.eugene.cmcclient.data.Backend
import com.eugene.cmcclient.data.tickers.Ticker
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Eugene on 10.01.2018.
 */
class NetworkDataSource(private val backend: Backend) : DataSourceTickers {
    override fun reset() {}

    override fun getTickers(from: Int, limit: Int): Observable<List<Ticker>> {
        return backend.getTickers(from, limit)
                .retry(2)
                .delay(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
    }

}