package com.eugene.cmcclient.data

import com.eugene.cmcclient.data.entities.Ticker
import com.eugene.cmcclient.data.repo.Tickers
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.invoke.ConstantCallSite

/**
 * Created by Eugene on 07.12.2017.
 */
interface CMCApiService {
    @GET(value = "ticker")
//    fun getTickers(@Query("start") start: Int, @Query("limit") limit: Int): Call<ResponseBody>
    fun getTickers(@Query("start") start: Int, @Query("limit") limit: Int): Observable<List<Ticker>>
}