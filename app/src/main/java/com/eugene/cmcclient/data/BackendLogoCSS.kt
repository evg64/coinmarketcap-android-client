package com.eugene.cmcclient.data

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Created by Eugene on 13.01.2018.
 */
interface BackendLogoCSS {
    @GET(value = DataConstants.PATH_CSS_LOGO_POSITIONS)
    fun getLogosCSS(): Observable<ResponseBody>
}