package com.eugene.cmcclient.di.components

import com.eugene.cmcclient.data.Backend
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.di.ScopeApp
import com.eugene.cmcclient.di.component_cache.ComponentCache
import com.eugene.cmcclient.di.modules.ModuleComponentCache
import com.eugene.cmcclient.di.modules.ModuleNetwork
import com.eugene.cmcclient.di.modules.ModuleRepo
import dagger.Component

/**
 * Created by Eugene on 07.12.2017.
 */
@Component(modules = [ModuleNetwork::class, ModuleRepo::class, ModuleComponentCache::class])
@ScopeApp
interface ComponentApp {
    fun getApiService() : Backend
    fun getTickerRepo() : RepositoryTickers
    fun getComponentCache() : ComponentCache
}