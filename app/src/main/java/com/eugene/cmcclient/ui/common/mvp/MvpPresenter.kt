package com.eugene.cmcclient.ui.common.mvp

/**
 * Created by Eugene on 07.12.2017.
 */
interface MvpPresenter<in V : MvpView> {
    fun attach(view: V)
    fun detach(view: V)
}