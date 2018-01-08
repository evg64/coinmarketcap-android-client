package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugene.cmcclient.R
import com.eugene.cmcclient.base.BaseMvpFragment
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.model.TickerModel
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_ticker_list.*
import javax.inject.Inject


/**
 * Created by Eugene on 09.12.2017.
 */
class FragmentTickerList : BaseMvpFragment(), MvpTickerList.View {
    override fun getFirstVisibleItem(): Int {
        return layoutManager.findFirstVisibleItemPosition()
    }

    override fun scrollTo(itemPosition: Int) {
        layoutManager.scrollToPositionWithOffset(itemPosition, 0)
    }

    @Inject lateinit var presenter: MvpTickerList.Presenter

    @Inject lateinit var adapter: AdapterTickerList

    private lateinit var scrollObservable: Observable<RecyclerViewScrollEvent>

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

//    TO DO
    // fragment onListItemsBelowScreen, remove from view: "get item count", "get last visible item"
//    repository pattern - introduce datasource, its factory
//    move logic from presenter to use case
//    use case - generic Params (arguments for use case)
//    dagger - activity component and fragment component are created for every new fragment, can presenter live inside fragment component? or we need upper layer? (presenter layer?)
    // implement pull-to-refresh
//    can we implement UseCaseErrorHandler + default presenter-wide error handler?
// different layout for portrait and landscape
    // consider presenter.onErrorMessageConsumed(errMsgId: Int)
    // handle permissions on >=marshmallow

//    TO CHECK

    override fun showLoading() {
        adapter.showLoadingView = true
    }

    override fun hideLoading() {
        adapter.showLoadingView = false
    }

    override fun getItemCount(): Int {
        return adapter.itemCount
    }

    override fun getItemCountBelowLastVisibleItem(): Int {
        val lastVisible = layoutManager.findLastVisibleItemPosition()
        return adapter.itemCount - lastVisible
    }

    override fun showMoreTickers(t: List<TickerModel>) {
        val oldSize = adapter.items.size
        adapter.items.addAll(t)
        adapter.notifyItemRangeChanged(oldSize, adapter.itemCount - oldSize)
    }

    override fun getScrollEvents(): Observable<RecyclerViewScrollEvent> {
        return scrollObservable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_ticker_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        scrollObservable = RxRecyclerView.scrollEvents(rv)
        presenter.onActivityCreated()
    }

    override fun onDestroyView() {
        // we must remove adapter otherwise activity leaks (since adapter survives config changes)
        rv.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.detach(this)
        super.onDestroy()
    }

}
