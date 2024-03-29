package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.graphics.Paint
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import com.eugene.cmcclient.R
import com.eugene.cmcclient.ui.common.mvp.BaseMvpFragment
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.model.TickerUIModel
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import android.util.Log
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.schedulers.Schedulers
import java.nio.ByteBuffer


class FragmentTickerList : BaseMvpFragment(), MvpTickerList.View {

    @Inject lateinit var presenter: MvpTickerList.Presenter

    @Inject lateinit var adapter: AdapterTickerList

    private lateinit var pullToRefresh: SwipeRefreshLayout
    private lateinit var rv: RecyclerView

    private var itemsBelowScreenObservable: BehaviorSubject<Int> = BehaviorSubject.create()

    private lateinit var recyclerRefreshObservable: Observable<Any>

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
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
        adapter.clearItems()
        adapter.notifyDataSetChanged()
    }

    override fun getItemCountBelowLastVisibleItem() = adapter.itemCount - layoutManager.findLastVisibleItemPosition()

    override fun showMoreTickers(t: List<TickerUIModel>) {
        val oldSize = adapter.getTickerCount()
//        val oldSize = adapter.items.size
        adapter.addItems(t)
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
        setHasOptionsMenu(true)
        component.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_refresh -> {
                presenter.onMenuRefreshPressed()
                true
            }
//            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ticker_list, container, false).also {
            pullToRefresh = it.findViewById(R.id.pullToRefresh)
        }
    }

    var itemCountBelowLastVisible = Integer.MIN_VALUE

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv = requireView().findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        rv.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val tmp = getItemCountBelowLastVisibleItem()
                if (itemCountBelowLastVisible != tmp) {
                    itemCountBelowLastVisible = tmp
                    itemsBelowScreenObservable.onNext(tmp)
                }
            }
        })
//        itemsBelowScreenObservable = RxRecyclerView.scrollEvents(rv)
//                .map { getItemCountBelowLastVisibleItem() }
//                .distinctUntilChanged()
        recyclerRefreshObservable = RxSwipeRefreshLayout.refreshes(pullToRefresh)
        presenter.attach(this)
//        presenter.onActivityCreated()
    }

    override fun onDestroyView() {
        presenter.detach(this)
        // we must remove adapter otherwise activity leaks (since adapter survives config changes)
        rv.adapter = null
        super.onDestroyView()
    }

}
