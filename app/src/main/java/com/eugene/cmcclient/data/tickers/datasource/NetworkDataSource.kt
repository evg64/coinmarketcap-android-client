package com.eugene.cmcclient.data.tickers.datasource

import android.util.Log
import com.eugene.cmcclient.data.Backend
import com.eugene.cmcclient.data.tickers.model.TickerDataModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Eugene on 10.01.2018.
 */
class NetworkDataSource(private val backend: Backend) : DataSourceTickers {
    override fun reset() {}

    override fun getTickers(from: Int, limit: Int): Observable<List<TickerDataModel>> {
        return backend.getTickers(from, limit)
                .retry(2)
                .toObservable()
                .doOnNext{
                    Log.d("DATA", "Received next $limit elements")
                }
                .subscribeOn(Schedulers.io())
    }

}