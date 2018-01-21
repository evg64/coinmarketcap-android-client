package com.eugene.cmcclient.ui.common.formatters

import java.text.DecimalFormat

/**
 * Created by Eugene on 19.01.2018.
 */
abstract class DecimalFormatter<T>(require2DecimalDigits: Boolean) : FormatterWithRes<T>() {
    protected val decimalFormatter = DecimalFormat(
            if (require2DecimalDigits) "###,##0.00" else "###,##0.##"
    )

    init {
        with(decimalFormatter.decimalFormatSymbols) {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
    }
}