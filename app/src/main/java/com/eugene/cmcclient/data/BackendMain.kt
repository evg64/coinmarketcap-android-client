package com.eugene.cmcclient.data

import com.eugene.cmcclient.data.tickers.model.TickerDataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Eugene on 07.12.2017.
 */
interface BackendMain {
    @GET(value = "ticker")
    fun getTickers(
            @Query("start") start: Int,
            @Query("limit") limit: Int
    ): Single<List<TickerDataModel>>
}