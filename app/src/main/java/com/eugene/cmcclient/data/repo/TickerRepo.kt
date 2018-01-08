package com.eugene.cmcclient.data.repo

import com.eugene.cmcclient.data.entities.Ticker
import io.reactivex.Observable

/**
 * Created by Eugene on 09.12.2017.
 */
interface TickerRepo : Repo<Ticker>{
    fun getTickers(from: Int, limit: Int) : Observable<List<Ticker>>
}