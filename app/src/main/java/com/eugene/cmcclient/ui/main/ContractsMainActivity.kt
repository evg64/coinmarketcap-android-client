package com.eugene.cmcclient.ui.main

import android.os.Bundle
import com.eugene.cmcclient.base.MvpLifecycleAwareMvpView
import com.eugene.cmcclient.base.MvpPresenter
import com.eugene.cmcclient.base.MvpView
import com.eugene.cmcclient.ui.model.TickerModel
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import io.reactivex.Observable

/**
 * Created by Eugene on 07.12.2017.
 */
class MvpActivityMain {
    interface View : MvpView
    interface Presenter : MvpPresenter<View>
}

class MvpTickerList {
    interface View : MvpLifecycleAwareMvpView {
        fun showMoreTickers(t: List<TickerModel>)
        fun getItemCountBelowLastVisibleItem(): Int
        fun getScrollEvents(): Observable<RecyclerViewScrollEvent>
        fun getItemCount(): Int
        fun getFirstVisibleItem(): Int
        fun scrollTo(itemPosition: Int)
    }

    interface Presenter : MvpPresenter<View> {
        fun onActivityCreated()
    }
}