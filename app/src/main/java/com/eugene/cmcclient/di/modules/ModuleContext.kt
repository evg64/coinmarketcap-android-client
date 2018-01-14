package com.eugene.cmcclient.di.modules

import android.content.Context
import com.eugene.cmcclient.di.ScopeApp
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Eugene on 14.01.2018.
 */
@Module
class ModuleContext constructor (context: Context) {
    val context = context.applicationContext

    @Provides @ScopeApp @Named("AppContext") fun provideAppContext(): Context = context
}