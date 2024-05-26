package com.javimay.rickmortyapp.di

import com.javimay.rickmortyapp.BuildConfig
import com.javimay.rickmortyapp.data.api.IDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IDataService = retrofit.create(IDataService::class.java)

}