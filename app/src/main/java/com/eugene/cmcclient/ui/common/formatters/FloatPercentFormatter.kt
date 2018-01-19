package com.eugene.cmcclient.ui.common.formatters

/**
 * Created by Eugene on 18.01.2018.
 */
class FloatPercentFormatter : Formatter<Float> {
    override fun format(what: Float): String {
        return "$what%"
    }
}