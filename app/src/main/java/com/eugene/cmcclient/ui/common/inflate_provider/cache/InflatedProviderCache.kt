package com.eugene.cmcclient.ui.common.inflate_provider.cache

import android.view.View
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Eugene on 20.02.2018.
 */
class InflatedProviderCache constructor(private val viewTypeCount: Int) {
    private val cache: MutableMap<Int, Deque<View>> = ConcurrentHashMap()

    init {
        for (i in 0 until viewTypeCount) cache[i] = ArrayDeque()
    }

    fun pop(viewType: Int): View? {
        if (viewType >= viewTypeCount) {
            throw IllegalArgumentException("ViewType ($viewType) should be less than viewTypeCount ($viewTypeCount)")
        }
        return cache[viewType]?.pop()
    }

    fun push(viewType: Int, view: View) {
        cache[viewType]?.push(view)
    }

    fun reset() {
        cache.clear()
    }
}