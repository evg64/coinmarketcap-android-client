package com.eugene.cmcclient.di.components

import com.eugene.cmcclient.di.ScopeFragment
import com.eugene.cmcclient.di.modules.ModuleFragment
import com.eugene.cmcclient.ui.main.MvpTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.AdapterTickerList
import com.eugene.cmcclient.ui.main.fragment_ticker_list.FragmentTickerList
import dagger.Component

/**
 * Created by Eugene on 13.12.2017.
 */
@Component(
    modules = [ModuleFragment::class],
    dependencies = [ComponentApp::class],
)
@ScopeFragment
interface ComponentFragment : CacheableComponent {

    fun getPresenterFragmentTickerList() : MvpTickerList.Presenter

    fun getAdapterTickerList() : AdapterTickerList

    fun inject(fragment : FragmentTickerList)

    fun inject(presenter: MvpTickerList.Presenter)
}