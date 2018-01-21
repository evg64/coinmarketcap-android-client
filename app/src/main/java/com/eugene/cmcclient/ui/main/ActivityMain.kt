package com.eugene.cmcclient.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.eugene.cmcclient.R
import com.eugene.cmcclient.ui.common.mvp.BaseMvpActivity
import com.eugene.cmcclient.ui.main.fragment_ticker_list.FragmentTickerList
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ActivityMain : BaseMvpActivity(), MvpActivityMain.View {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    companion object {
        val TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT"
    }

    @Inject lateinit var presenter: MvpActivityMain.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.attach(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment, FragmentTickerList(), TAG_MAIN_FRAGMENT).commit()
        }
    }

    override fun onDestroy() {
        presenter.detach(this)
        super.onDestroy()
    }

}
