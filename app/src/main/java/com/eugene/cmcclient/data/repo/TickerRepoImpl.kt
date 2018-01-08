package com.eugene.cmcclient.data.repo

import com.eugene.cmcclient.data.CMCApiService
import com.eugene.cmcclient.data.entities.Ticker
import io.reactivex.Observable

/**
 * Created by Eugene on 09.12.2017.
 */
class TickerRepoImpl(private val service: CMCApiService) : TickerRepo {
    private val cacheTickers: MutableList<Ticker> = ArrayList()
    override fun getTickers(from: Int, limit: Int): Observable<List<Ticker>> {
        return if (cacheTickers.size >= from + limit) {
            Observable.just(cacheTickers.subList(from, from + limit))
        } else {
            return service.getTickers(from, limit)
                    .doOnNext({
                                  if (from < cacheTickers.size) {
                                      var index = from
                                      for (ticker in it) {
                                          cacheTickers[index++] = ticker
                                      }
                                  } else {
                                      cacheTickers.addAll(it)
                                  }
                              })
        }
    }
}