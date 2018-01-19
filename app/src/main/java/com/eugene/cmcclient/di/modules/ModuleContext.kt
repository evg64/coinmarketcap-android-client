package com.eugene.cmcclient.di.modules

import android.content.Context
import com.eugene.cmcclient.di.AppContext
import com.eugene.cmcclient.di.ScopeApp
import dagger.Module
import dagger.Provides

/**
 * Created by Eugene on 14.01.2018.
 */
@Module
class ModuleContext constructor (context: Context) {
    private val context = context.applicationContext

    @Provides @ScopeApp @AppContext fun provideAppContext(): Context = context

    @Provides @ScopeApp fun provideResources(@AppContext context: Context) = context.resources
}