package com.eugene.cmcclient.data.tickers.repository

import com.eugene.cmcclient.data.tickers.model.Ticker
import io.reactivex.Observable

/**
 * Created by Eugene on 09.12.2017.
 */
interface RepositoryTickers {
    fun getTickers(from: Int, limit: Int) : Observable<List<Ticker>>
    fun reset()
}