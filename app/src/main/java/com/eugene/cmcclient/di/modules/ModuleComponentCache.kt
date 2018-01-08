package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.di.component_cache.ComponentCache
import com.eugene.cmcclient.di.component_cache.MutableMapComponentCache
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 08.01.2018.
 */
@Module
class ModuleComponentCache {
    @Provides fun provideComponentCache(): ComponentCache = MutableMapComponentCache()
}