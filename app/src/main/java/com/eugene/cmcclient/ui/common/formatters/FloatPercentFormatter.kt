package com.eugene.cmcclient.ui.common.formatters

/**
 * Created by Eugene on 18.01.2018.
 */
class FloatPercentFormatter : FormatterWithRes<Float>() {
    override fun format(what: Float) = "$what%"

    fun formatTwice(stringRes: Int, what: Float): String = res.getString(stringRes, format(what))
}