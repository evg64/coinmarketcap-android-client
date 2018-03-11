package com.eugene.cmcclient.ui.common.formatters

import java.text.DecimalFormat

/**
 * Created by Eugene on 19.01.2018.
 */
abstract class DecimalFormatter<T>(private val require2DecimalDigits: Boolean) : FormatterWithRes<T>() {
    private val decimalFormatter2Digits = DecimalFormat("###,##0.00")

    private val decimalFormatter = DecimalFormat("###,##0.##")

    /**
     * @return proper formatter for this value
     */
    protected open fun getDecimalFormatter(): DecimalFormat {
        return if (require2DecimalDigits)
            decimalFormatter2Digits
        else
            decimalFormatter
    }

    init {
        with(decimalFormatter.decimalFormatSymbols) {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
    }
}