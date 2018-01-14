package com.eugene.cmcclient.data.logo.repository

import android.graphics.Bitmap
import com.eugene.cmcclient.data.tickers.model.Name
import io.reactivex.Observable
import io.reactivex.Single
import java.net.URI

/**
 * Created by Eugene on 13.01.2018.
 */
interface RepositoryLogo {
    fun getLogoMap(): Observable<Map<Name, Bitmap>>
}