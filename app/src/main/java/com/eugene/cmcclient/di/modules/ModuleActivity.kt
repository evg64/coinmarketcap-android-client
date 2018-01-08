package com.eugene.cmcclient.di.modules

import com.eugene.cmcclient.di.ScopeActivity
import com.eugene.cmcclient.ui.main.MvpActivityMain
import com.eugene.cmcclient.ui.main.PresenterMainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 09.12.2017.
 */
@Module
class ModuleActivity {
    @ScopeActivity
    @Provides
    fun provideMainActivityPresenter(presenter : PresenterMainActivity) : MvpActivityMain.Presenter = presenter
}