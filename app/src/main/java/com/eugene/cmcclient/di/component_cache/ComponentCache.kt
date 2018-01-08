package com.eugene.cmcclient.di.component_cache

import android.os.Bundle
import com.eugene.cmcclient.di.components.CacheableComponent

/**
 * Created by Eugene on 08.01.2018.
 */
interface ComponentCache {

    companion object {
        /**
         * Cache uses this key to store (in savedInstanceState) information about index of component, associated with cache's client (callee).
         *
         * So this key is reserved by cache and clients must not use this key for other needs.
         */
        const val KEY_COMPONENT_CACHE_INDEX = "ComponentCache_KEY_COMPONENT_CACHE_INDEX"
    }

    fun storeComponent(component: CacheableComponent, savedInstanceState: Bundle)

    fun restoreComponent(savedInstanceState: Bundle): CacheableComponent?

}