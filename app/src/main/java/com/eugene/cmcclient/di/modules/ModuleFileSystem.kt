package com.eugene.cmcclient.di.modules

import android.content.Context
import com.eugene.cmcclient.di.AppContext
import com.eugene.cmcclient.di.FilesRoot
import com.eugene.cmcclient.di.ScopeApp
import dagger.Module
import dagger.Provides
import java.io.File

/**
 * Created by Eugene on 16.01.2018.
 */
@Module (includes = [ModuleContext::class])
class ModuleFileSystem {
    @ScopeApp @Provides @FilesRoot fun provideRootStorage(@AppContext context: Context): File? =
        context.getExternalFilesDir(null)
}