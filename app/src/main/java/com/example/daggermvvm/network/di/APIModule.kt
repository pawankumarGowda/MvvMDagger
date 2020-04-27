package com.example.daggermvvm.network.di

import android.icu.util.TimeUnit
import com.example.daggermvvm.network.repository.RetrofitRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import javax.xml.datatype.DatatypeConstants.SECONDS

@Module
class APIModule constructor(baseURL:String)  {
    var baseURL:String?=""
    init {
        this.baseURL = baseURL
    }

    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return  OkHttpClient.Builder()
            .readTimeout(1200, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(1200, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    }
    @Provides
    fun provideGSON(): GsonConverterFactory {

        return  GsonConverterFactory.create()

    }

    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,okHttpClient: OkHttpClient):Retrofit{
        return     Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRetroRepository(): RetrofitRepository {
        return RetrofitRepository()
    }


}