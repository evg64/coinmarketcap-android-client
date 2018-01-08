package com.eugene.cmcclient.di

import com.eugene.cmcclient.di.components.*
import com.eugene.cmcclient.di.modules.ModuleFragment

/**
 * Created by Eugene on 12.12.2017.
 */
object Injector {
    @Suppress("MemberVisibilityCanPrivate")
    val componentApp: ComponentApp by lazy { DaggerComponentApp.builder().build() }

    fun newComponentActivity(): ComponentActivity {
        return DaggerComponentActivity.builder()
                .componentApp(componentApp)
                .build()
    }

    fun newComponentFragment(): ComponentFragment {
        return DaggerComponentFragment.builder()
                .componentApp(componentApp)
                .moduleFragment(ModuleFragment())
                .build()
    }
}