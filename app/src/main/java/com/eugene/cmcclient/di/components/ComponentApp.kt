package com.eugene.cmcclient.di.components

import android.content.Context
import com.eugene.cmcclient.data.Backend
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.di.AppContext
import com.eugene.cmcclient.di.FilesRoot
import com.eugene.cmcclient.di.ScopeApp
import com.eugene.cmcclient.di.component_cache.ComponentCache
import com.eugene.cmcclient.di.modules.ModuleComponentCache
import com.eugene.cmcclient.di.modules.ModuleFileSystem
import com.eugene.cmcclient.di.modules.ModuleNetwork
import com.eugene.cmcclient.di.modules.ModuleRepo
import dagger.Component
import java.io.File

/**
 * Created by Eugene on 07.12.2017.
 */
@Component(modules = [ModuleNetwork::class, ModuleRepo::class, ModuleComponentCache::class, ModuleFileSystem::class])
@ScopeApp
interface ComponentApp {
    fun getApiService() : Backend
    fun getTickerRepo() : RepositoryTickers
    fun getComponentCache() : ComponentCache
    @FilesRoot fun getRootStorage(): File

    @AppContext fun getAppContext(): Context
}