package com.eugene.cmcclient.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Eugene on 07.12.2017.
 */
open class BaseMvpPresenter<V : MvpView> : MvpPresenter<V> {
    var view : V? = null
    protected var compositeDisposable: CompositeDisposable? = null

    override fun attach(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    override fun detach(view: V) {
        if (this.view == view) {
            this.view = null
        }
        compositeDisposable?.dispose()
        compositeDisposable = null
    }
}