package com.eugene.cmcclient.ui.common.inflate_provider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.eugene.cmcclient.ui.common.inflate_provider.cache.InflatedProviderCache
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Inflates views into cache
 */
internal class AsyncInflater {

    private val chain: BehaviorSubject<Task> = BehaviorSubject.create()

    init {
        chain.map {
            for (i in 0 until it.count) {
                val view = it.inflater.inflate(it.layoutId, it.parent, false)
                it.cache.push(it.viewType, view)
            }
        }.subscribeOn(Schedulers.computation()).subscribe()
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
        chain.onNext(Task(inflater, parent, cache, layoutId, viewType, count))
    }

    private class Task(val inflater: LayoutInflater,
                       val parent: ViewGroup,
                       val cache: InflatedProviderCache,
                       val layoutId: Int,
                       val viewType: Int,
                       val count: Int)
}