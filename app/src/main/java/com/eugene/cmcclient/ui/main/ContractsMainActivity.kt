package com.eugene.cmcclient.ui.main

import com.eugene.cmcclient.ui.common.MvpPresenter
import com.eugene.cmcclient.ui.common.MvpView
import com.eugene.cmcclient.ui.model.TickerUIModel
import io.reactivex.Observable

/**
 * Created by Eugene on 07.12.2017.
 */
class MvpActivityMain {
    interface View : MvpView
    interface Presenter : MvpPresenter<View>
}

class MvpTickerList {
    interface View : MvpView {
        fun showMoreTickers(t: List<TickerUIModel>)
        fun getItemCountBelowLastVisibleItem(): Int
        fun getItemCount(): Int
        fun getItemsBelowScreenEvents(): Observable<Int>
        fun getPullToRefreshEvents(): Observable<Any>
        fun resetTickers()
    }

    interface Presenter : MvpPresenter<View> {
        fun onActivityCreated()
    }
}