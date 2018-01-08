package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.os.Bundle
import com.eugene.cmcclient.R
import com.eugene.cmcclient.base.BaseMvpPresenter
import com.eugene.cmcclient.data.entities.Ticker
import com.eugene.cmcclient.data.repo.TickerRepo
import com.eugene.cmcclient.di.ScopeFragment
import com.eugene.cmcclient.ui.UIConstants
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.model.TickerModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Eugene on 09.12.2017.
 */
@ScopeFragment
class PresenterTickerList @Inject constructor(private val tickerRepo: TickerRepo)
                                                : BaseMvpPresenter<MvpTickerList.View>(),
                                                  MvpTickerList.Presenter {

    private var disposableScroll: Disposable? = null

    override fun onActivityCreated() {
        when {
            needsMoreTickers() ->
                loadMoreTickers(view!!.getItemCount(), true, UIConstants.PAGE_SIZE)
            else -> subscribeOnScroll()
        }
    }

    override fun detach(view: MvpTickerList.View) {
        if (view == this.view) {
            disposableScroll = null
        }
        super.detach(view)
    }

    private fun needsMoreTickers(): Boolean {
        return view?.getItemCountBelowLastVisibleItem() ?: 0 < UIConstants.ADAPTER_ITEMS_BELOW_SCREEN_THRESHOLD
    }

    private fun subscribeOnScroll() {
        if (disposableScroll == null) {
            disposableScroll = view?.getScrollEvents()?.subscribe(
                {
                    if (needsMoreTickers() && view != null) {
                        loadMoreTickers(view!!.getItemCount(), true, UIConstants.PAGE_SIZE)
                    }
                }
            )
        }
    }

    private fun unsubscribeFromScroll() {
        disposableScroll?.dispose()
        disposableScroll = null
    }

    private fun loadMoreTickers(from: Int, showLoading: Boolean, itemCount: Int) {
        if (showLoading) {
            view?.showLoading()
            unsubscribeFromScroll()
        }
        val onNext = { list: List<TickerModel> ->
            val v = view
            if (v != null) {
                v.showMoreTickers(list)
                if (needsMoreTickers()) {
                    loadMoreTickers(from + UIConstants.PAGE_SIZE,
                                    false,
                                    UIConstants.PAGE_SIZE)
                } else {
                    v.hideLoading()
                    subscribeOnScroll()
                }
            }
        }
        val onError: (Throwable) -> Unit = { t: Throwable ->
            t.printStackTrace()
            view?.hideLoading()
            subscribeOnScroll()
            view?.showError(R.string.failed_to_load_tickers)
        }
        val disposable =
                tickerRepo.getTickers(from, itemCount)
                        .map { it: List<Ticker> -> TickerModel.from(it) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onNext, onError)
        compositeDisposable?.add(disposable)
    }

}