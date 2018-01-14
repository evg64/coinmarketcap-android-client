package com.eugene.cmcclient.di

import android.content.Context
import com.eugene.cmcclient.di.components.*
import com.eugene.cmcclient.di.modules.ModuleContext
import com.eugene.cmcclient.di.modules.ModuleFragment

/**
 * Created by Eugene on 12.12.2017.
 */
object Injector {
    @Suppress("MemberVisibilityCanPrivate")
    lateinit var componentApp: ComponentApp

    fun initComponentApp(context: Context) {
        componentApp = DaggerComponentApp.builder().moduleContext(ModuleContext(context.applicationContext)).build()
    }

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