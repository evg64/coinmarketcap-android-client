package com.eugene.cmcclient.data.tickers.datasource

import com.eugene.cmcclient.data.tickers.Ticker
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Returns the same (hot) observable if there is pending operation with required params.
 *
 * Created by Eugene on 12.01.2018.
 */
class HotDataSource(private val source: DataSourceTickers) : DataSourceTickers {
    private data class GetTickersParams(val from: Int, val limit: Int)

    private val ongoingOperations: MutableMap<GetTickersParams, PublishSubject<List<Ticker>>> = HashMap()

    override fun getTickers(from: Int, limit: Int): Observable<List<Ticker>> {
        val key = GetTickersParams(from, limit)
        val operation = ongoingOperations[key]
        return if (operation == null) {
            val subject: PublishSubject<List<Ticker>> = PublishSubject.create()
            source.getTickers(from, limit)
                    .doOnDispose({ ongoingOperations.remove(key) })
                    .doOnError({ ongoingOperations.remove(key) })
                    .doOnComplete({ ongoingOperations.remove(key) })
                    .subscribe(subject)
            ongoingOperations[key] = subject
            subject
        } else {
            operation
        }
    }

    override fun reset() {
        ongoingOperations.forEach({ it.value.onError(InterruptedException("Data source is reset")) })
        ongoingOperations.clear()
        source.reset()
    }

}