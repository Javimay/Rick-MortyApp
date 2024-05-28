package com.javimay.rickmortyapp.di

import com.javimay.rickmortyapp.data.repository.character.CharacterRepositoryImpl
import com.javimay.rickmortyapp.data.repository.episode.EpisodeRepositoryImpl
import com.javimay.rickmortyapp.data.repository.location.LocationRepositoryImpl
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import com.javimay.rickmortyapp.domain.repository.ILocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface IRepositoryModule {

    @Binds
    fun provideCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): ICharacterRepository

    @Binds
    fun provideEpisodeRepository(
        episodeRepositoryImpl: EpisodeRepositoryImpl
    ): IEpisodeRepository

    @Binds
    fun provideLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): ILocationRepository
}