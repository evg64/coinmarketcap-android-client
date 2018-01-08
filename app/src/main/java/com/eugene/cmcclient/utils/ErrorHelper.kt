package com.eugene.cmcclient.utils

import android.util.Log
import com.eugene.cmcclient.BuildConfig
import io.reactivex.internal.schedulers.NewThreadScheduler

/**
 * Created by Eugene on 12.12.2017.
 */
object ErrorHelper {
    val TAG = "CMC_CLIENT_ERROR"

    /**
     * By calling this method we don't have to throw exceptions in production, however, we do want app to signal these problems loudly in debug mode.
     */
    fun signalError(errorDescription : String) {
        if (BuildConfig.DEBUG) {
            throw RuntimeException(errorDescription, Throwable())
        } else {
            Log.e(TAG, errorDescription)
        }
    }
}