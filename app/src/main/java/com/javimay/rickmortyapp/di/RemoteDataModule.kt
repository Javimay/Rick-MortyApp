package com.javimay.rickmortyapp.di

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterRemoteDataSource
import com.javimay.rickmortyapp.data.repository.character.datasourceimpl.CharacterRemoteDataSourceImpl
import com.javimay.rickmortyapp.data.repository.data.datasource.IDataRemoteDataSource
import com.javimay.rickmortyapp.data.repository.data.datasourceimpl.DataRemoteDataSourceImpl
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeRemoteDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasourceImpl.EpisodeRemoteDataSourceImpl
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationRemoteDataSource
import com.javimay.rickmortyapp.data.repository.location.datasourceimpl.LocationRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Singleton
    @Provides
    fun provideDataRemoteDataSource(dataService: IDataService): IDataRemoteDataSource =
        DataRemoteDataSourceImpl(dataService)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(dataService: IDataService): ICharacterRemoteDataSource =
        CharacterRemoteDataSourceImpl(dataService)

    @Singleton
    @Provides
    fun provideEpisodeRemoteDataSource(dataService: IDataService): IEpisodeRemoteDataSource =
        EpisodeRemoteDataSourceImpl(dataService)

    @Singleton
    @Provides
    fun provideLocationRemoteDataSource(dataService: IDataService): ILocationRemoteDataSource =
        LocationRemoteDataSourceImpl(dataService)
}