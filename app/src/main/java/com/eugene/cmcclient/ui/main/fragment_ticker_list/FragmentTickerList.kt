package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugene.cmcclient.R
import com.eugene.cmcclient.ui.common.BaseMvpFragment
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.model.TickerUIModel
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_ticker_list.*
import javax.inject.Inject

class FragmentTickerList : BaseMvpFragment(), MvpTickerList.View {

    @Inject lateinit var presenter: MvpTickerList.Presenter

    @Inject lateinit var adapter: AdapterTickerList

    private lateinit var itemsBelowScreenObservable: Observable<Int>

    private lateinit var recyclerRefreshObservable: Observable<Any>

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun showLoading() {
        val hasItems = adapter.itemCount > 0
        adapter.showLoadingView = hasItems
        pullToRefresh.isRefreshing = !hasItems
    }

    override fun hideLoading() {
        pullToRefresh.isRefreshing = false
        adapter.showLoadingView = false
    }

    override fun getItemCount(): Int {
        return adapter.itemCount
    }

    override fun resetTickers() {
        adapter.items.clear()
        adapter.notifyDataSetChanged()
    }

    override fun getItemCountBelowLastVisibleItem() = adapter.itemCount - layoutManager.findLastVisibleItemPosition()

    override fun showMoreTickers(t: List<TickerUIModel>) {
        val oldSize = adapter.items.size
        adapter.items.addAll(t)
        adapter.notifyItemRangeChanged(oldSize, adapter.itemCount - oldSize)
    }

    override fun getItemsBelowScreenEvents(): Observable<Int> {
        return itemsBelowScreenObservable
    }

    override fun getPullToRefreshEvents(): Observable<Any> {
        return recyclerRefreshObservable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.attach(this)
    }

    //    TO DO
    // make logo access for tickers
    // make normal layout for tickers
    // add refresh to action bar
    // different layout for portrait and landscape
    // handle permissions on >=marshmallow
    // refactor repository logo - move out code to datasources

    //    TO CHECK

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_ticker_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        itemsBelowScreenObservable = RxRecyclerView.scrollEvents(rv)
                .map { getItemCountBelowLastVisibleItem() }
                .distinctUntilChanged()
        recyclerRefreshObservable = RxSwipeRefreshLayout.refreshes(pullToRefresh)
        presenter.onActivityCreated()
    }

    override fun onDestroyView() {
        presenter.detach(this)
        // we must remove adapter otherwise activity leaks (since adapter survives config changes)
        rv.adapter = null
        super.onDestroyView()
    }

}
