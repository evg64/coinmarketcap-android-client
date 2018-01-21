package com.eugene.cmcclient.ui.common.formatters

/**
 * Created by Eugene on 19.01.2018.
 */
class DoubleFormatter(require2DecimalDigits: Boolean) : DecimalFormatter<Double>(require2DecimalDigits) {
    override fun format(what: Double): String {
        return decimalFormatter.format(what)
    }
}