package com.eugene.cmcclient.base

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
        internalCache = if (savedInstanceState == null || instance == null || instance !is ComponentCache) {
            Injector.componentApp.getComponentCache()
        } else {
            // cache is supposed to exist only after config changes, so we must check that saved instance state exists
            // moreover, if custom non config instance is somehow absent or is of improper type, we will fallback to creating new cache instance
            instance
        }
        super.onCreate(savedInstanceState)
    }

    /**
     * This method should not be overriden by subclasses, it is used to retain component cache
     */
    override final fun onRetainCustomNonConfigurationInstance(): ComponentCache {
        return internalCache
    }

    override fun storeComponent(component: CacheableComponent, savedInstanceState: Bundle) {
        internalCache.storeComponent(component, savedInstanceState)
    }

    override fun restoreComponent(savedInstanceState: Bundle): CacheableComponent? {
        return internalCache.restoreComponent(savedInstanceState)
    }
}