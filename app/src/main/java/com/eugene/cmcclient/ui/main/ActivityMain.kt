package com.eugene.cmcclient.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.eugene.cmcclient.R
import com.eugene.cmcclient.ui.common.BaseMvpActivity
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.detach(this)
        super.onDestroy()
    }

}
