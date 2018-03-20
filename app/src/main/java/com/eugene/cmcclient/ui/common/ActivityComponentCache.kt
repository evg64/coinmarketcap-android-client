package com.eugene.cmcclient.ui.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eugene.cmcclient.di.Injector
import com.eugene.cmcclient.di.component_cache.ComponentCache
import com.eugene.cmcclient.di.components.CacheableComponent

/**
 * Uses [onRetainCustomNonConfigurationInstance] to make dagger components of activity and all its fragments live through configuration changes.
 */
open class ActivityComponentCache : AppCompatActivity(), ComponentCache {

    private lateinit var internalCache: ComponentCache

    override fun onCreate(savedInstanceState: Bundle?) {
        val instance = lastCustomNonConfigurationInstance
        internalCache = if (instance == null || instance !is ComponentCache) {
            // new cache instance
            Injector.componentApp.getComponentCache()
        } else {
            instance
        }
        super.onCreate(savedInstanceState)
    }

    /**
     * This method should not be overriden by subclasses, it is used to retain component cache
     */
    final override fun onRetainCustomNonConfigurationInstance(): ComponentCache {
        return internalCache
    }

    override fun storeComponent(component: CacheableComponent, savedInstanceState: Bundle) {
        internalCache.storeComponent(component, savedInstanceState)
    }

    override fun restoreComponent(savedInstanceState: Bundle): CacheableComponent? {
        return internalCache.restoreComponent(savedInstanceState)
    }
}