package com.eugene.cmcclient.ui.common.formatters

import com.eugene.cmcclient.data.tickers.model.Symbol

/**
 * Created by Eugene on 18.01.2018.
 */
class SymbolFormatter: Formatter<Symbol> {
    override fun format(what: Symbol): String {
        return when (what) {
            Symbol.USD -> "$"
            else -> what.name
        }
    }
}