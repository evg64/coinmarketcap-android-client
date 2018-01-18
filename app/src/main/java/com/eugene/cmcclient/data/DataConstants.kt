package com.eugene.cmcclient.data

import android.os.Environment

/**
 * Created by Eugene on 13.01.2018.
 */
object DataConstants {
    const val CMC_ROOT = "https://coinmarketcap.com/"
    const val PATH_CSS_LOGO_POSITIONS = "/static/public/sprites/currencies_views_all_0.css"
    val PATH_LOGO_SINGLE_BITMAP = "file://" + Environment.getExternalStorageDirectory().absolutePath + "/currencies.png"
    //    const val PATH_LOGO_SINGLE_BITMAP = CMC_ROOT + "static/public/sprites/currencies_views_all_0.png"
}