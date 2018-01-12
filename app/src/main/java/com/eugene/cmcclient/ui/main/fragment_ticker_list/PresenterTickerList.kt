package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.util.Log
import com.eugene.cmcclient.R
import com.eugene.cmcclient.data.tickers.Ticker
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.di.ScopeFragment
import com.eugene.cmcclient.ui.UIConstants
import com.eugene.cmcclient.ui.common.BaseMvpPresenter
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.model.TickerModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ScopeFragment
class PresenterTickerList @Inject constructor(private val tickerRepo: RepositoryTickers)
    : BaseMvpPresenter<MvpTickerList.View>(),
      MvpTickerList.Presenter {

    private var disposableScroll: Disposable? = null
    private var disposableLoadTickers: Disposable? = null

    override fun onActivityCreated() {
        view?.let {
            when {
                needsMoreTickers(it.getItemCountBelowLastVisibleItem()) ->
                    loadTickers(it.getItemCount(), true, UIConstants.PAGE_SIZE)
                else -> subscribeOnScroll()
            }
            val subscription = it.getPullToRefreshEvents().subscribe(this::onPullToRefresh)
            compositeDisposable?.add(subscription)
        }
    }

    private fun onPullToRefresh(unused: Any) {
        tickerRepo.reset()
        disposableLoadTickers?.dispose()
        disposableLoadTickers = null
        view?.let {
            it.resetTickers()
            it.hideLoading()
            loadTickers(0, true, UIConstants.PAGE_SIZE)
        }
    }

    override fun detach(view: MvpTickerList.View) {
        if (view == this.view) {
            disposableScroll = null
            disposableLoadTickers = null
        }
        super.detach(view)
    }

    private fun needsMoreTickers(itemsBelowScreen: Int): Boolean {
        return itemsBelowScreen < UIConstants.ADAPTER_ITEMS_BELOW_SCREEN_THRESHOLD
    }

    private fun subscribeOnScroll() {
        if (disposableScroll == null) {
            disposableScroll = view?.getItemsBelowScreenEvents()?.subscribe(
                    {
                        Log.d("TAG", "Item count below screen: $it")
                        if (view != null && needsMoreTickers(it)) {
                            loadTickers(view!!.getItemCount(), true, UIConstants.PAGE_SIZE)
                        }
                    }
            )
        }
    }

    private fun unsubscribeFromScroll() {
        disposableScroll?.dispose()
        disposableScroll = null
    }

    private fun loadTickers(from: Int, showLoading: Boolean, itemCount: Int) {
        if (showLoading) {
            view?.showLoading()
            unsubscribeFromScroll()
        }
        val onNext: (List<TickerModel>) -> Unit = { list: List<TickerModel> ->
            view?.let {
                it.showMoreTickers(list)
                if (needsMoreTickers(it.getItemCountBelowLastVisibleItem())) {
                    loadTickers(from + UIConstants.PAGE_SIZE,
                                false,
                                UIConstants.PAGE_SIZE)
                } else {
                    it.hideLoading()
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
        disposableLoadTickers = tickerRepo.getTickers(from, itemCount)
                        .map { it: List<Ticker> -> TickerModel.from(it) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onNext, onError)
        compositeDisposable?.add(disposableLoadTickers!!)
    }

}