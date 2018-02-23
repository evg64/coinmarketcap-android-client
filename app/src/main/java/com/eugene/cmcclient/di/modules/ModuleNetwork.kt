package com.eugene.cmcclient.di.modules

import android.content.Context
import android.util.Log
import com.eugene.cmcclient.data.BackendLogoCSS
import com.eugene.cmcclient.data.BackendLogoCSSAssets
import com.eugene.cmcclient.data.BackendMain
import com.eugene.cmcclient.data.DataConstants
import com.eugene.cmcclient.di.AppContext
import com.eugene.cmcclient.di.ScopeApp
import com.jakewharton.picasso.OkHttp3Downloader
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by Eugene on 14.12.2017.
 */
@Module(includes = [ModuleContext::class])
class ModuleNetwork {
    @Provides @ScopeApp fun provideBackend(@Named("MainBackend") retrofit: Retrofit): BackendMain {
        return retrofit.create(BackendMain::class.java)
    }

    @Provides @ScopeApp fun provideBackendCSS(
            @Named("LogosBackend") retrofit: Retrofit,
            @AppContext context: Context
    ): BackendLogoCSS {
        return BackendLogoCSSAssets(context)
//        return retrofit.create(BackendLogoCSS::class.java)
    }

    @Provides @ScopeApp @Named("MainBackend") fun provideRetrofit(callAdapterFactory: CallAdapter.Factory, converterFactory: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .baseUrl("https://api.coinmarketcap.com/v1/")
                .client(client)
                .build()
    }

    @Provides @ScopeApp @Named("LogosBackend") fun provideLogoRetrofit(callAdapterFactory: CallAdapter.Factory, converterFactory: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .baseUrl(DataConstants.CMC_ROOT)
                .client(client)
                .build()
    }

    @Provides @ScopeApp fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides @ScopeApp fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val result = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("CMCNetwork", it)
        })
        result.level = HttpLoggingInterceptor.Level.BODY
        return result
    }

    @Provides @ScopeApp fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()
    @Provides @ScopeApp fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides @ScopeApp fun providePicasso(@AppContext context: Context): Picasso {
        return Picasso.Builder(context).downloader(OkHttp3Downloader(context)).build()
    }
}