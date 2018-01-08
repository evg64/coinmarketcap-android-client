package com.eugene.cmcclient.base

import android.arch.lifecycle.LifecycleObserver

/**
 * Created by Eugene on 31.12.2017.
 */
abstract class ObservingMvpPresenter<View : MvpLifecycleAwareMvpView> : BaseMvpPresenter<View>(),
                                                                        LifecycleObserver {
    override fun attach(view: View) {
        super.attach(view)
        view.getLifecycle().addObserver(this)
    }
}