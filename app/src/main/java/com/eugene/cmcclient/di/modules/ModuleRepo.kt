package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.data.Backend
import com.eugene.cmcclient.data.tickers.cache.InMemoryCacheTickers
import com.eugene.cmcclient.data.tickers.datasource.DataSourceTickers
import com.eugene.cmcclient.data.tickers.datasource.SyncCacheDataSource
import com.eugene.cmcclient.data.tickers.datasource.DataSourceTickersNetwork
import com.eugene.cmcclient.data.tickers.datasource.HotDataSource
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickersWithDataSource
import com.eugene.cmcclient.di.ScopeApp
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 14.12.2017.
 */
@Module(includes = [ModuleNetwork::class])
class ModuleRepo {
    @ScopeApp @Provides fun provideTickerRepo(dataSource: DataSourceTickers)
            : RepositoryTickers = RepositoryTickersWithDataSource(dataSource)

    @ScopeApp @Provides fun provideDataSource(
            backend: Backend,
            inMemoryCache: InMemoryCacheTickers
    ): DataSourceTickers {
        var source: DataSourceTickers
        source = DataSourceTickersNetwork(backend)
        source = HotDataSource(source)
        source = SyncCacheDataSource(source, inMemoryCache)
        return source
    }

    @ScopeApp @Provides fun provideInMemoryCacheTickers()
            : InMemoryCacheTickers = InMemoryCacheTickers()
}