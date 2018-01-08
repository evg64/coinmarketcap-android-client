package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.di.ScopeFragment
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.AdapterTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.PresenterTickerList
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 13.12.2017.
 */
@Module
@ScopeFragment
class ModuleFragment {
    @ScopeFragment
    @Provides
    fun providePresenterFragmentTickerList(presenter: PresenterTickerList): MvpTickerList.Presenter = presenter

    @ScopeFragment
    @Provides
    fun provideAdapterTickerList(): AdapterTickerList = AdapterTickerList()

}