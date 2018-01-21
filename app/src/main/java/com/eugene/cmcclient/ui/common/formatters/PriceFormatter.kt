package com.eugene.cmcclient.ui.common.formatters

import com.eugene.cmcclient.R
import com.eugene.cmcclient.data.tickers.model.Price

/**
 * Created by Eugene on 18.01.2018.
 */
class PriceFormatter : DecimalFormatter<Price>(true) {
    private val symbolFormatter = SymbolFormatter()

    override fun format(what: Price): String {
        return res.getString(
                R.string.price,
                decimalFormatter.format(what.value),
                symbolFormatter.format(what.nominatedIn))
    }
}