package com.eugene.cmcclient.base

import android.app.AlertDialog
import android.content.Context
import android.view.View

/**
 * Created by Eugene on 15.12.2017.
 */
interface DefaultMvpView : MvpView {
    fun getLoadingView(): View
    fun getContext(): Context?

    override fun showLoading() {
        getLoadingView().visibility = View.VISIBLE
    }

    override fun hideLoading() {
        getLoadingView().visibility = View.GONE
    }

    override fun showError(msg: String) {
        if (getContext() != null) {
            AlertDialog.Builder(getContext()).setMessage(msg).show()
        }
    }

    override fun showError(stringResId: Int, vararg formatArgs: Any) {
        val context = getContext()
        if (context != null) {
            val msg = context.getString(stringResId, formatArgs)
            showError(msg)
        }
    }
}