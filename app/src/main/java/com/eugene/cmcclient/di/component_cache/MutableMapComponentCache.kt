package com.eugene.cmcclient.di.component_cache

import android.os.Bundle
import com.eugene.cmcclient.di.components.CacheableComponent

class MutableMapComponentCache : ComponentCache {
    val components = mutableMapOf<Int, CacheableComponent>()

    private var nextKey = 0

    companion object {
        /**
         * Indicates that bundle does not contain cache index
         */
        private const val INDEX_BUNDLE_HAS_NO_CACHE_INDEX = -1
    }

    override fun storeComponent(component: CacheableComponent, savedInstanceState: Bundle) {
        components[nextKey] = component
        savedInstanceState.putInt(ComponentCache.KEY_COMPONENT_CACHE_INDEX, nextKey++)
    }

    override fun restoreComponent(savedInstanceState: Bundle): CacheableComponent? {
        val cacheIndex = savedInstanceState.getInt(ComponentCache.KEY_COMPONENT_CACHE_INDEX, INDEX_BUNDLE_HAS_NO_CACHE_INDEX)
        return if (cacheIndex == INDEX_BUNDLE_HAS_NO_CACHE_INDEX) null else components[cacheIndex]
    }
}