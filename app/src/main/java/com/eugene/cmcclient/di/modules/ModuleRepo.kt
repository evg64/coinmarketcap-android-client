package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.data.CMCApiService
import com.eugene.cmcclient.data.repo.TickerRepo
import com.eugene.cmcclient.data.repo.TickerRepoImpl
import com.eugene.cmcclient.di.ScopeApp
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 14.12.2017.
 */
@Module(includes = [ModuleNetwork::class])
class ModuleRepo {
    @ScopeApp @Provides fun getTickerRepo(service : CMCApiService) : TickerRepo = TickerRepoImpl(service)
}