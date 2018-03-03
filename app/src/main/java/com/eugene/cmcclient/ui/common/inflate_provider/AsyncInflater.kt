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
    private var worker = newWorker()

    private fun newWorker() = Executors.newSingleThreadExecutor {
        val result = Thread(it)
        result.priority = Thread.MIN_PRIORITY
        result
    }

    fun inflateSingle(
            inflater: LayoutInflater,
            parent: ViewGroup?,
            cache: InflatedProviderCache,
            layoutId: Int,
            viewType: Int) {
        inflateSeveral(inflater, parent, cache, layoutId, viewType, 1)
    }

    fun inflateSeveral(inflater: LayoutInflater,
                parent: ViewGroup?,
                cache: InflatedProviderCache,
                layoutId: Int,
                viewType: Int,
                count: Int) {
        worker.submit(Task(inflater, parent, cache, layoutId, viewType, count, this))
    }

    private class Task(val inflater: LayoutInflater,
                       val parent: ViewGroup?,
                       val cache: InflatedProviderCache,
                       val layoutId: Int,
                       val viewType: Int,
                       val count: Int,
                       val monitor: AsyncInflater) : Runnable {
        init {
            Log.d("TAG", "")
        }

        override fun run() {
            synchronized(monitor, {
                try {
                    with(Thread.currentThread()) {
                        Log.d("Thread", "Tread id=$id, priority=$priority")
                    }
                    for (i in 0 until count) {
                        val view = inflater.inflate(layoutId, parent, false)
                        cache.push(viewType, view)
                    }
                } catch (e: InterruptedException) {
                    Log.d("Thread", "Tread was interrupted")
                }
            })
        }

    }

    fun cancelAllTasks() {
        synchronized(this, {
            worker.shutdownNow()
            worker = newWorker()
            })
    }
}