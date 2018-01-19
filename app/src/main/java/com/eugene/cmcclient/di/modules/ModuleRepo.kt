package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.data.BackendLogoCSS
import com.eugene.cmcclient.data.BackendMain
import com.eugene.cmcclient.data.logo.repository.RepositoryLogo
import com.eugene.cmcclient.data.logo.repository.RepositoryLogoSprites
import com.eugene.cmcclient.data.tickers.adapter.MapperTickerApiToDomain
import com.eugene.cmcclient.data.tickers.cache.CacheTickers
import com.eugene.cmcclient.data.tickers.cache.InMemoryCacheTickers
import com.eugene.cmcclient.data.tickers.datasource.DataSourceTickers
import com.eugene.cmcclient.data.tickers.datasource.HotDataSource
import com.eugene.cmcclient.data.tickers.datasource.NetworkDataSource
import com.eugene.cmcclient.data.tickers.datasource.SyncCachingDataSource
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickers
import com.eugene.cmcclient.data.tickers.repository.RepositoryTickersWithDataSource
import com.eugene.cmcclient.di.ScopeApp
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 14.12.2017.
 */
@Module(includes = [ModuleNetwork::class])
class ModuleRepo {
    @ScopeApp @Provides fun provideTickerRepo(
            dataSource: DataSourceTickers,
            repositoryLogo: RepositoryLogo,
            adapter: MapperTickerApiToDomain
    ): RepositoryTickers = RepositoryTickersWithDataSource(dataSource, repositoryLogo, adapter)

    @ScopeApp @Provides fun provideDataSource(backend: BackendMain, cache: CacheTickers)
            : DataSourceTickers {
        var source: DataSourceTickers
        source = NetworkDataSource(backend)
        source = HotDataSource(source)
        source = SyncCachingDataSource(source, cache)
        return source
    }

    @ScopeApp @Provides fun provideRepositoryLogo(backend: BackendLogoCSS, picasso: Picasso): RepositoryLogo = RepositoryLogoSprites(backend, picasso)

    @ScopeApp @Provides fun provideAdapterTickerApiToDomain()
            : MapperTickerApiToDomain = MapperTickerApiToDomain()

    @ScopeApp @Provides fun provideInMemoryCacheTickers(): CacheTickers = InMemoryCacheTickers()
}