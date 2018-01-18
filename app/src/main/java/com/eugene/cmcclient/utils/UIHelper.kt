package com.eugene.cmcclient.utils

import android.util.TypedValue
import com.eugene.cmcclient.di.Injector

/**
 * Created by Eugene on 16.01.2018.
 */
object UIHelper {

    fun dpToPx(dp: Int): Int {
        val res = Injector.componentApp.getAppContext().resources
        val px: Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), res.displayMetrics)
        return px.toInt()
    }
}