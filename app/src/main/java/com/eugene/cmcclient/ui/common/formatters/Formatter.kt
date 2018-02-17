package com.eugene.cmcclient.ui.common.formatters

/**
 * Created by Eugene on 18.01.2018.
 */
interface Formatter<in T> {
    fun format(what: T): String
}