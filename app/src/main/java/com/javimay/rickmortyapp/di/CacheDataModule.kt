package com.javimay.rickmortyapp.di

import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterCacheDataSource
import com.javimay.rickmortyapp.data.repository.character.datasourceimpl.CharacterCacheDataSourceImpl
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeCacheDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasourceImpl.EpisodeCacheDataSourceImpl
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationCacheDataSource
import com.javimay.rickmortyapp.data.repository.location.datasourceimpl.LocationCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CacheDataModule {

    @Provides
    fun provideCharacterCacheDataSource(): ICharacterCacheDataSource {
        return CharacterCacheDataSourceImpl()
    }

    @Provides
    fun provideEpisodeCacheDataSource(): IEpisodeCacheDataSource {
        return EpisodeCacheDataSourceImpl()
    }

    @Provides
    fun provideLocationCacheDataSource(): ILocationCacheDataSource {
        return LocationCacheDataSourceImpl()
    }

}