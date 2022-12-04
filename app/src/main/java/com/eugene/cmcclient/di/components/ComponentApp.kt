package com.eugene.cmcclient.di.components

import android.content.Context
import android.content.res.Resources
import com.eugene.cmcclient.data.BackendMain
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.di.AppContext
import com.eugene.cmcclient.di.FilesRoot
import com.eugene.cmcclient.di.ScopeApp
import com.eugene.cmcclient.di.component_cache.ComponentCache
import com.eugene.cmcclient.di.modules.*
import dagger.Component
import java.io.File

/**
 * Created by Eugene on 07.12.2017.
 */
@Component(modules = [ModuleNetwork::class, ModuleRepo::class, ModuleComponentCache::class, ModuleFileSystem::class, ModuleContext::class])
@ScopeApp
interface ComponentApp {
    fun getApiService() : BackendMain
    fun getTickerRepo() : RepositoryTickers
    fun getComponentCache() : ComponentCache
    @FilesRoot fun getRootStorage(): File?

    @AppContext fun getAppContext(): Context

    fun getRes(): Resources
}