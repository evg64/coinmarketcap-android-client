package com.eugene.cmcclient.ui.common.inflate_provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Is responsible for inflating views for recycler view in background
 */
interface InflatedViewsProvider {
    fun getView(inflater: LayoutInflater, parent: ViewGroup, viewType: Int) : View
    fun getViewTypeMap() : Map<Int, Int>
    fun cleanupViews()
}