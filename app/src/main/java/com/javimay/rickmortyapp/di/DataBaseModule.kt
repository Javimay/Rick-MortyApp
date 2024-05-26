package com.javimay.rickmortyapp.di

import android.content.Context
import androidx.room.Room
import com.javimay.rickmortyapp.data.db.RickMortyDataBase
import com.javimay.rickmortyapp.data.db.daos.ICharacterDao
import com.javimay.rickmortyapp.data.db.daos.IEpisodeDao
import com.javimay.rickmortyapp.data.db.daos.ILocationDao
import com.javimay.rickmortyapp.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context):RickMortyDataBase =
        Room.databaseBuilder(
            context, RickMortyDataBase::class.java, DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideCharacterDao(dataBase: RickMortyDataBase): ICharacterDao =
        dataBase.characterDao()

    @Singleton
    @Provides
    fun provideEpisodeDao(dataBase: RickMortyDataBase): IEpisodeDao =
        dataBase.episodeDao()

    @Singleton
    @Provides
    fun provideLocationDao(dataBase: RickMortyDataBase): ILocationDao =
        dataBase.locationDao()

}