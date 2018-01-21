package com.eugene.cmcclient.ui.common.formatters

import java.text.DecimalFormat

/**
 * Created by Eugene on 19.01.2018.
 */
abstract class DecimalFormatter<T> : FormatterWithRes<T>() {
    protected val decimalFormatter = DecimalFormat("###,##0.00")

    init {
        with(decimalFormatter.decimalFormatSymbols) {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
    }
}