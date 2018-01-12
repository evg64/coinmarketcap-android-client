package com.eugene.cmcclient.data

import com.eugene.cmcclient.data.tickers.Ticker
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Eugene on 07.12.2017.
 */
interface Backend {
    @GET(value = "ticker")
    fun getTickers(
            @Query("start") start: Int,
            @Query("limit") limit: Int
    ): Observable<List<Ticker>>
}