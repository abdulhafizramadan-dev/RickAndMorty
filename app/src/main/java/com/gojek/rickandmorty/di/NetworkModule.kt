package com.gojek.rickandmorty.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.gojek.characters.data.remote.RickAndMortyApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class NetworkModule {
    @Provides
    fun providesChuckerHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()
    }

    @Provides
    fun providesRetrofit(chuckerOkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(chuckerOkHttpClient)
            .build()
    }

    @Provides
    fun providesRickAndMortyApi(retrofit: Retrofit): RickAndMortyApi {
        return retrofit.create()
    }
}