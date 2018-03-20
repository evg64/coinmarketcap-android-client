package com.eugene.cmcclient.ui.common.formatters

import com.eugene.cmcclient.R
import com.eugene.cmcclient.data.tickers.model.Price
import java.text.DecimalFormat

/**
 * Created by Eugene on 18.01.2018.
 */
class PriceFormatter : DecimalFormatter<Price>(true) {
    private val symbolFormatter = SymbolFormatter()

    private val decimalFormatForSmallDigits = DecimalFormat("###,##0.000##")

        private fun getDecimalFormatter(value: Float): DecimalFormat {
            return if (value < 0.1f)
                decimalFormatForSmallDigits
            else
                super.getDecimalFormatter()
        }

        override fun format(what: Price): String {
            return res.getString(
                    R.string.price,
                    getDecimalFormatter(what.value).format(what.value),
                    symbolFormatter.format(what.nominatedIn))
        }
    }