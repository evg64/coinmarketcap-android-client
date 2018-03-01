package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.os.trace
import com.eugene.cmcclient.BuildConfig
import com.eugene.cmcclient.databinding.TickerBinding
import com.eugene.cmcclient.ui.common.inflate_provider.CachedInflatedViewsProvider
import com.eugene.cmcclient.ui.model.TickerUIModel
import org.jetbrains.anko.layoutInflater
import javax.inject.Inject


class AdapterTickerList @Inject constructor(private var viewProvider: CachedInflatedViewsProvider): RecyclerView.Adapter<AdapterTickerList.Holder>() {
    private val items: MutableList<TickerUIModel> = mutableListOf()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        val inflater: LayoutInflater = recyclerView?.context?.layoutInflater!!
        viewProvider.setupCache(inflater, recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        viewProvider.cleanupViews()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    fun addItems(items: List<TickerUIModel>) {
        this.items.addAll(items)
        updateCount()
    }

    fun clearItems() {
        items.clear()
        updateCount()
    }

    fun getTickerCount() = items.size

    object ViewTypes {
        const val TICKER = 0
        const val LOADING = 1
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        trace("ATL#onBindViewHolder", {
            val start = System.nanoTime()
            holder?.onBind(position)
            val p = System.nanoTime()
            val passed = (p.toDouble() - start.toDouble()) / 1000.0
            Log.d("Bind", "position $position, time $passed microseconds")
        })
    }

    companion object {
        @BindingAdapter("bind:imageBitmap")
        fun loadImage(iv: ImageView, bitmap: Bitmap?) {
            if (bitmap != null) {
                iv.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        with(Thread.currentThread()) {
            Log.d("Thread", "Tread id=$id, priority=$priority")
        }
        return trace("ATL#onCreateViewHolder", {
            val inflater: LayoutInflater = parent?.context?.layoutInflater!!
            val v = viewProvider.getView(inflater, parent, viewType)
            when (viewType) {
                ViewTypes.TICKER -> HolderTicker(v)
                ViewTypes.LOADING -> HolderLoading(v)
                else -> throw IllegalArgumentException("Unsupported view type " + viewType)
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if (showLoadingView && position == itemCount - 1) ViewTypes.LOADING else ViewTypes.TICKER
    }

    override fun getItemCount(): Int {
        return count
    }

    private var count: Int = 0

    private fun updateCount() {
        count = items.size + if (showLoadingView) 1 else 0
    }

    abstract inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(position: Int)
    }

    inner class HolderTicker(view: View) : Holder(view) {
        override fun onBind(position: Int) {
            binding.ticker = items[position]
        }

        private val binding: TickerBinding = DataBindingUtil.bind(itemView)
    }

    inner class HolderLoading(view: View) : Holder(view) {
        override fun onBind(position: Int) {
            Log.d("TAG", "bind loading, position $position")
        }
    }

    var showLoadingView = false
        set(value) {
            if (value != field) {
                if (value) {
                    notifyItemInserted(itemCount - 1)
                } else {
                    notifyItemChanged(itemCount - 1)
                }
            }
            field = value
            updateCount()
        }

    override fun onFailedToRecycleView(holder: Holder?): Boolean {
        if (BuildConfig.DEBUG) {
            throw RuntimeException("Failed to recycle $holder")
        }
        return super.onFailedToRecycleView(holder)
    }
}