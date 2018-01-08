package com.eugene.cmcclient.base

/**
 * Created by Eugene on 07.12.2017.
 */
interface MvpView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showError(stringResId : Int, vararg formatArgs : Any)
}