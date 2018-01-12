package com.eugene.cmcclient.di.components

import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.di.ScopeActivity
import com.eugene.cmcclient.di.modules.ModuleActivity
import com.eugene.cmcclient.ui.main.ActivityMain
import dagger.Component

/**
 * Created by Eugene on 07.12.2017.
 */
@Component(modules = [ModuleActivity::class], dependencies = [ComponentApp::class])
@ScopeActivity
interface ComponentActivity : CacheableComponent {
    fun inject(activity : ActivityMain)
    fun getTickerRepo() : RepositoryTickers
}