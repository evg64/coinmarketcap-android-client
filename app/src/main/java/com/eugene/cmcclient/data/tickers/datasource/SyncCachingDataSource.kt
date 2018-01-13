package com.eugene.cmcclient.data.tickers.datasource

import com.eugene.cmcclient.data.tickers.TickerDataModel
import com.eugene.cmcclient.data.tickers.cache.CacheTickers
import io.reactivex.Observable

/**
 * At first, tries to get data from cache synchronously. If no data is received, falls back to original data source.
 *
 * Also, caches response from original data source (cache may implement it synchronously or not).
 *
 * Created by Eugene on 10.01.2018.
 */
class SyncCachingDataSource(
        private val source: DataSourceTickers,
        private val cache: CacheTickers
) : DataSourceTickers {

    override fun getTickers(from: Int, limit: Int): Observable<List<TickerDataModel>> {
        val cached = cache.get(from, limit)
        return if (cached != null)
            Observable.just(cached)
        else
            source.getTickers(from, limit)
                    .doOnNext({ cache.put(it, from) })
    }

    override fun reset() {
        cache.reset()
        source.reset()
    }

}