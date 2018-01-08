package com.eugene.cmcclient.base

import android.arch.lifecycle.Lifecycle

/**
 * Created by Eugene on 31.12.2017.
 */
interface MvpLifecycleAwareMvpView : MvpView {
    fun getLifecycle(): Lifecycle
}