package com.gojek.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesChuckerHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(chuckerOkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(chuckerOkHttpClient)
            .build()
    }
}