package com.eugene.cmcclient

import android.app.Application
import android.os.StrictMode
import android.os.Trace


/**
 * Created by Eugene on 07.12.2017.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Trace.beginSection("rosietn")
        Trace.endSection()

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                                               .detectAll()
                                               .penaltyLog()
                                               .penaltyDeath()
                                               .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                                           .detectAll()
                                           .penaltyLog()
                                           .penaltyDeath()
                                           .build())
        }

    }
}