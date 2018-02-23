package com.eugene.cmcclient.ui.common.inflate_provider

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.eugene.cmcclient.ui.common.inflate_provider.cache.InflatedProviderCache
import java.util.concurrent.Executors

/**
 * Inflates views into cache
 */
internal class AsyncInflater {

//    private val worker = Executors.newSingleThreadExecutor()
    private val worker = Executors.newSingleThreadExecutor {
        val result = Thread(it)
        result.priority = Thread.MIN_PRIORITY
        result
    }

    fun inflateSingle(
            inflater: LayoutInflater,
            parent: ViewGroup,
            cache: InflatedProviderCache,
            layoutId: Int,
            viewType: Int) {
        inflateSeveral(inflater, parent, cache, layoutId, viewType, 1)
    }

    fun inflateSeveral(inflater: LayoutInflater,
                parent: ViewGroup,
                cache: InflatedProviderCache,
                layoutId: Int,
                viewType: Int,
                count: Int) {
        val task = Task(inflater, parent, cache, layoutId, viewType, count)
        worker.submit(task)
    }

    private class Task(val inflater: LayoutInflater,
                       val parent: ViewGroup,
                       val cache: InflatedProviderCache,
                       val layoutId: Int,
                       val viewType: Int,
                       val count: Int) : Runnable {
        init {
            Log.d("TAG", "")
        }

        override fun run() {
            with(Thread.currentThread()) {
                Log.d("Thread", "Tread id=$id, priority=$priority")
            }
            for (i in 0 until count) {
                val view = inflater.inflate(layoutId, parent, false)
                cache.push(viewType, view)
            }
        }

    }
}