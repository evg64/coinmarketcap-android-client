package com.eugene.cmcclient.ui.common.formatters

import com.eugene.cmcclient.R
import com.eugene.cmcclient.data.tickers.model.Quantity

/**
 * Created by Eugene on 18.01.2018.
 */
open class VolumeFormatter : DecimalFormatter<Quantity>(false) {

    private val symbolFormatter = SymbolFormatter()

    override fun format(what: Quantity): String {
        return res.getString(
                R.string.volume,
                decimalFormatter.format(what.value),
                symbolFormatter.format(what.nominatedIn)
        )
    }
}