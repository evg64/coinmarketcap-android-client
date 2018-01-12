package com.eugene.cmcclient.ui.common

import android.app.AlertDialog
import android.os.Bundle
import com.eugene.cmcclient.di.Injector
import com.eugene.cmcclient.di.components.ComponentActivity

/**
 * Created by Eugene on 07.12.2017.
 */
abstract class BaseMvpActivity : ActivityComponentCache(),
                                 MvpView {
    lateinit var component: ComponentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = if (savedInstanceState == null) {
            Injector.newComponentActivity()
        } else {
            // if cache returns wrong value or nothing, we become stateless and create new dagger component every onCreate call
            restoreComponent(savedInstanceState) as? ComponentActivity ?: Injector.newComponentActivity()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            storeComponent(component, outState)
        }
    }

    override fun showError(msg: String) {
        AlertDialog.Builder(this).setMessage(msg).show()
    }

    override fun showError(stringResId: Int, vararg formatArgs: Any) {
        val msg = getString(stringResId, formatArgs)
        showError(msg)
    }
}