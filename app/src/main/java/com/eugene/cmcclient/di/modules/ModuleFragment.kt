package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.di.ScopeFragment
import com.eugene.cmcclient.ui.common.inflate_provider.CachedInflatedViewsProvider
import com.eugene.cmcclient.ui.common.inflate_provider.InflatedViewsProvider
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.AdapterTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.InflatedViewsProviderTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.PresenterTickerList
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Eugene on 13.12.2017.
 */
@Module
class ModuleFragment {
    @ScopeFragment
    @Provides
    fun providePresenterFragmentTickerList(presenter: PresenterTickerList): MvpTickerList.Presenter = presenter

    @ScopeFragment
    @Provides
    fun provideAdapterTickerList(@Named("ATL") viewProvider: CachedInflatedViewsProvider): AdapterTickerList = AdapterTickerList(viewProvider)

    @ScopeFragment
    @Provides
    @Named("ATL")
    fun provideViewProvider(): CachedInflatedViewsProvider = InflatedViewsProviderTickerList()
}