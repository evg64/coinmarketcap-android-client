package com.eugene.cmcclient.ui.main.fragment_ticker_list

import com.eugene.cmcclient.R
import com.eugene.cmcclient.ui.common.inflate_provider.InflatedViewsProviderAsync

/**
 * Created by Eugene on 20.02.2018.
 */
class InflatedViewsProviderTickerList : InflatedViewsProviderAsync(viewTypes, cacheSizes) {
    companion object {
        val viewTypes = mapOf(
                AdapterTickerList.ViewTypes.TICKER to R.layout.ticker,
                AdapterTickerList.ViewTypes.LOADING to R.layout.loading_adapter_item
        )
        val cacheSizes = mapOf(
                AdapterTickerList.ViewTypes.TICKER to R.layout.ticker,
                AdapterTickerList.ViewTypes.LOADING to R.layout.loading_adapter_item
        )
    }
}