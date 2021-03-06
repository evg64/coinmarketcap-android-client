package com.eugene.cmcclient.ui.common.inflate_provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugene.cmcclient.ui.common.inflate_provider.cache.InflatedProviderCache

/**
 * Provides views for recycler view adapter. Tries to create views in background to avoid [LayoutInflater.inflate] calls on UI thread.
 */
open class InflatedViewsProviderAsync(
        private val mViewTypesToLayoutIds: Map<Int, Int>,
        private val mCacheSizes: Map<Int, Int>
) : CachedInflatedViewsProvider {

    override fun cleanupViews() {
        inflater.cancelAllTasks()
        cache.reset()
    }

    val cache: InflatedProviderCache = InflatedProviderCache(mViewTypesToLayoutIds.size)

    private val inflater = AsyncInflater()

    override fun getCacheSizes(): Map<Int, Int> {
        return mCacheSizes
    }

    override fun getViewTypeMap(): Map<Int, Int> {
        return mViewTypesToLayoutIds
    }

    private fun getLayoutId(viewType: Int): Int =
            getViewTypeMap()[viewType] ?:
            throw IllegalArgumentException("There is no layout id for viewType $viewType")

    override fun getView(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): View {
        val cached = cache.pop(viewType)
        val layoutId = getLayoutId(viewType)
        return if (cached == null) {
            inflater.inflate(layoutId, parent, false)
        } else {
            this.inflater.inflateSingle(inflater, parent, cache, layoutId, viewType)
            cached
        }
    }

    override fun setupCacheAsync(inflater: LayoutInflater, parent: ViewGroup?) {
        for (entry in getCacheSizes().entries) {
            val viewType = entry.key
            val cacheSize = entry.value
            val layoutId = getLayoutId(viewType)
            this.inflater.inflateSeveral(inflater, parent, cache, layoutId, viewType, cacheSize)
        }
    }
}