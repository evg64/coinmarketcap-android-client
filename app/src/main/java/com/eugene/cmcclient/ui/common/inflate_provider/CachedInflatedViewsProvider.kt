package com.eugene.cmcclient.ui.common.inflate_provider

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Eugene on 20.02.2018.
 */
interface CachedInflatedViewsProvider : InflatedViewsProvider {
    fun setupCache(inflater: LayoutInflater, parent: ViewGroup?)
    fun getCacheSizes(): Map<Int, Int>
}