package com.test.testceibalistusers.di


import com.test.testceibalistusers.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    @Named("Retrofit1")
    fun provideRetrofitApi1(
    ): Retrofit {
           return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()
    }

    @Singleton
    @Provides
    fun provideApiServiceApi1(
        @Named("Retrofit1")
        retrofit: Retrofit
    ): ApiService =
        retrofit.create(ApiService::class.java)


}