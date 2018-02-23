package com.eugene.cmcclient.data

import android.content.Context
import io.reactivex.Observable
import okhttp3.ResponseBody
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by Eugene on 20.02.2018.
 */
class BackendLogoCSSAssets(private val ctx: Context) : BackendLogoCSS {
    override fun getLogosCSS(): Observable<ResponseBody> {
        return Observable.fromCallable {
            var inputStream: InputStream? = null
            try {
                inputStream = ctx.assets.open("logo.css")
                val body =  IOUtils.toString(inputStream, Charset.forName("UTF-8"))
                ResponseBody.create(null, body)
            } finally {
                IOUtils.closeQuietly(inputStream)
            }
        }
    }
}