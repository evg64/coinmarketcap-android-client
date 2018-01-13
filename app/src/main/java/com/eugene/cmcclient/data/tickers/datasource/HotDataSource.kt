package com.eugene.cmcclient.data.tickers.datasource

import android.util.Log
import com.eugene.cmcclient.data.tickers.TickerFromApi
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Returns the same (hot) observable if there is pending operation with required params.
 *
 * Created by Eugene on 12.01.2018.
 */
class HotDataSource(private val source: DataSourceTickers) : DataSourceTickers {
    private data class GetTickersParams(val from: Int, val limit: Int)

    private val ongoingOperations: MutableMap<GetTickersParams, PublishSubject<List<TickerFromApi>>> = HashMap()

    override fun getTickers(from: Int, limit: Int): Observable<List<TickerFromApi>> {
        val key = GetTickersParams(from, limit)
        val operation = ongoingOperations[key]
        return if (operation == null) {
            // creating new hot observable
            startNewGetTickersOperation(key)
        } else {
            // returning observable of operation which is already in progress
            Log.d("DATA", "Returning cached subscription for params: $key")
            operation
        }
    }

    private fun startNewGetTickersOperation(key: GetTickersParams): PublishSubject<List<TickerFromApi>> {
        val subject: PublishSubject<List<TickerFromApi>> = PublishSubject.create()
        ongoingOperations[key] = subject
        source.getTickers(key.from, key.limit)
                .doOnDispose({ ongoingOperations.remove(key) })
                .doOnError({ ongoingOperations.remove(key) })
                .doOnComplete({ ongoingOperations.remove(key) })
                .subscribe(subject)
        Log.d("DATA", "Returning new subscription for params: $key")
        return subject
    }

    override fun reset() {
        Log.d("DATA", "Resetting subscriptions")
        ongoingOperations.forEach({ it.value.onError(InterruptedException("Data source is reset")) })
        ongoingOperations.clear()
        source.reset()
    }

}