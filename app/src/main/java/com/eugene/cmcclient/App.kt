package com.eugene.cmcclient

import android.app.Application
import android.content.res.Resources
import android.os.Handler
import android.os.StrictMode
import android.os.Trace
import android.util.Log
import com.eugene.cmcclient.di.Injector
import com.facebook.stetho.Stetho
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


/**
 * Created by Eugene on 07.12.2017.
 */
class App : Application() {

    companion object {
        var res: Resources? = null
    }

    override fun onCreate() {
        super.onCreate()
        res = resources
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
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

        Injector.initComponentApp(this)
//        val publish: PublishSubject<Long> = PublishSubject.create()
//        val rootChain = Observable.interval(1, TimeUnit.SECONDS).doOnNext({ Log.d("RX", "First chain: $it") })
//        rootChain.subscribe(publish)
//        rootChain.subscribe(
//                {
//                    Log.d("RX", "Root chain ")
//                }
//        )
//        val disposable2 = publish.subscribe({ Log.d("RX", "Second chain: $it") })
//        Observable.just(1).delay(3150, TimeUnit.MILLISECONDS).subscribe(
//                {
//                    disposable2.dispose()
//                    Log.d("RX", "Second disposable is disposed")
//                }
//        )
    }
}