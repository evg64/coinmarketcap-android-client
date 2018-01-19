package com.eugene.cmcclient.data.tickers.model

/**
 * Created by Eugene on 13.01.2018.
 */
data class Symbol(val name: String) {
    companion object {
        val USD = Symbol("USD")
    }
}