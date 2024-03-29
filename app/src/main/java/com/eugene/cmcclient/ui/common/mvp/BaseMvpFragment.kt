package com.eugene.cmcclient.ui.common.mvp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eugene.cmcclient.di.Injector
import com.eugene.cmcclient.di.component_cache.ComponentCache
import com.eugene.cmcclient.di.components.ComponentFragment

/**
 * Created by Eugene on 09.12.2017.
 */
abstract class BaseMvpFragment : Fragment(),
                                 MvpView {
    protected lateinit var component: ComponentFragment

    override fun showError(msg: String) {
        if (activity != null) {
            AlertDialog.Builder(activity).setMessage(msg).show()
        }
    }

    override fun showError(stringResId : Int, vararg formatArgs : Any) {
        if (activity != null) {
            showError(getString(stringResId, formatArgs))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = this.activity
        component = if (savedInstanceState == null || activity !is ComponentCache) {
            Injector.newComponentFragment()
        } else {
            // if cache returns wrong value or nothing, we become stateless and create new dagger component every onCreate call
            activity.restoreComponent(savedInstanceState) as? ComponentFragment ?: Injector.newComponentFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val activity = this.activity
        if (outState != null && activity is ComponentCache) {
            activity.storeComponent(component, outState)
        }
    }
}