package com.eugene.cmcclient.data.tickers.repository

import android.graphics.Bitmap
import com.eugene.cmcclient.data.logo.repository.RepositoryLogo
import com.eugene.cmcclient.data.tickers.adapter.MapperTickerApiToDomain
import com.eugene.cmcclient.data.tickers.datasource.DataSourceTickers
import com.eugene.cmcclient.data.tickers.model.StringId
import com.eugene.cmcclient.data.tickers.model.Ticker
import com.eugene.cmcclient.data.tickers.model.TickerDataModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by Eugene on 09.12.2017.
 */
open class RepositoryTickersWithDataSource(
        private val source: DataSourceTickers,
        private val repositoryLogo: RepositoryLogo,
        private val adapter: MapperTickerApiToDomain
) : RepositoryTickers {

    override fun reset() {
        source.reset()
    }

    override fun getTickers(from: Int, limit: Int): Observable<List<Ticker>> {
        return Observable.combineLatest(
                repositoryLogo.getLogoMap(),
                source.getTickers(from, limit),
                BiFunction {
                    logos: Map<StringId, Bitmap>, tickers: List<TickerDataModel> ->
                    adapter.transform(tickers, logos)
                }
        )
    }

}