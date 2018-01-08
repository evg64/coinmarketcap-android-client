package com.eugene.cmcclient.di.modules

import android.util.Log
import com.eugene.cmcclient.data.CMCApiService
import com.eugene.cmcclient.di.ScopeApp
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Eugene on 14.12.2017.
 */
@Module
class ModuleNetwork {
    @Provides @ScopeApp fun getApiService(retrofit: Retrofit): CMCApiService {
        return retrofit.create(CMCApiService::class.java)
    }

    @Provides @ScopeApp fun getRetrofit(callAdapterFactory: CallAdapter.Factory, converterFactory: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .baseUrl("https://api.coinmarketcap.com/v1/")
                .client(client)
                .build()
    }

    @Provides @ScopeApp fun getOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides @ScopeApp fun getHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val result = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("CMCNetwork", it)
        })
        result.level = HttpLoggingInterceptor.Level.HEADERS
        return result
    }

    @Provides @ScopeApp fun getConverterFactory(): Converter.Factory = GsonConverterFactory.create()
    @Provides @ScopeApp fun getCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()
}