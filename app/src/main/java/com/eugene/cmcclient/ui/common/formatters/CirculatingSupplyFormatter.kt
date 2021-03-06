package com.eugene.cmcclient.ui.common.formatters

import com.eugene.cmcclient.R

/**
 * Created by Eugene on 19.01.2018.
 */
class CirculatingSupplyFormatter: DecimalFormatter<Double>(false) {
    override fun format(what: Double): String {
        return res.getString(R.string.circulating_supply, getDecimalFormatter().format(what))
    }
}