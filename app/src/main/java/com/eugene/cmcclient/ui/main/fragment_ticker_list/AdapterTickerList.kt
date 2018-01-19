package com.eugene.cmcclient.ui.main.fragment_ticker_list

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.eugene.cmcclient.R
import com.eugene.cmcclient.databinding.TickerBinding
import com.eugene.cmcclient.ui.model.TickerUIModel
import org.jetbrains.anko.layoutInflater
import android.graphics.Bitmap
import android.databinding.BindingAdapter
import android.widget.ImageView


class AdapterTickerList : RecyclerView.Adapter<AdapterTickerList.Holder>() {
    val items: MutableList<TickerUIModel> = mutableListOf()

    object ViewTypes {
        const val TICKER = 0
        const val LOADING = 1
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val start = System.nanoTime()
        //holder?.onBind(position)
        val p = System.nanoTime()
        val passed = (p.toDouble() - start.toDouble()) / 1000.0
        Log.d("Bind", "position $position, time $passed microseconds")
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
        return when (viewType) {
            ViewTypes.TICKER -> {
                val v = parent?.context?.layoutInflater?.inflate(R.layout.ticker, parent, false)
                HolderTicker(v!!)
            }
            ViewTypes.LOADING -> {
                val v = parent?.context?.layoutInflater?.inflate(R.layout.loading_adapter_item, parent, false)
                HolderLoading(v!!)
            }
            else -> {
                throw IllegalArgumentException("Unsupported view type " + viewType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (showLoadingView && position == itemCount - 1) ViewTypes.LOADING else ViewTypes.TICKER
    }

    override fun getItemCount(): Int {
        return items.size + if (showLoadingView) 1 else 0
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
        }
}