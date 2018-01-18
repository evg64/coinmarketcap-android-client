package com.eugene.cmcclient.data.logo.repository

import android.graphics.Bitmap
import com.eugene.cmcclient.data.tickers.model.StringId
import io.reactivex.Observable

/**
 * Created by Eugene on 13.01.2018.
 */
interface RepositoryLogo {
    fun getLogoMap(): Observable<Map<StringId, Bitmap>>
}