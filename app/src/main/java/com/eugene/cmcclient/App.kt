package com.eugene.cmcclient

import android.app.Application
import android.os.Handler
import android.os.StrictMode
import android.os.Trace
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


/**
 * Created by Eugene on 07.12.2017.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

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

        val publish: PublishSubject<Long> = PublishSubject.create()
        val rootChain = Observable.interval(1, TimeUnit.SECONDS).doOnNext({ Log.d("RX", "First chain: $it") })
                rootChain.subscribe(publish)
        rootChain.subscribe(
                {
                    Log.d("RX", "Root chain ")
                }
        )
        val disposable2 = publish.subscribe({ Log.d("RX", "Second chain: $it") })
        Observable.just(1).delay(3150, TimeUnit.MILLISECONDS).subscribe(
                {
                    disposable2.dispose()
                    Log.d("RX", "Second disposable is disposed")
                }
        )
    }
}