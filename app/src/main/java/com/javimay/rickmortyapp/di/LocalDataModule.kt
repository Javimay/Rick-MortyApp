package com.javimay.rickmortyapp.di

import com.javimay.rickmortyapp.data.db.daos.ICharacterDao
import com.javimay.rickmortyapp.data.db.daos.IEpisodeDao
import com.javimay.rickmortyapp.data.db.daos.ILocationDao
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterLocalDataSource
import com.javimay.rickmortyapp.data.repository.character.datasourceimpl.CharacterLocalDataSourceImpl
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeLocalDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasourceImpl.EpisodeLocalDataSourceImpl
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationLocalDataSource
import com.javimay.rickmortyapp.data.repository.location.datasourceimpl.LocationLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideCharacterLocalDataSource(characterDao: ICharacterDao): ICharacterLocalDataSource =
        CharacterLocalDataSourceImpl(characterDao)

    @Singleton
    @Provides
    fun provideEpisodeLocalDataSource(episodeDao: IEpisodeDao): IEpisodeLocalDataSource =
        EpisodeLocalDataSourceImpl(episodeDao)

    @Singleton
    @Provides
    fun provideLocationLocalDataSource(locationDao: ILocationDao): ILocationLocalDataSource =
        LocationLocalDataSourceImpl(locationDao)

}