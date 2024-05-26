package com.javimay.rickmortyapp.data.repository.data.datasource

import com.javimay.rickmortyapp.data.model.Data
import retrofit2.Response

interface IDataRemoteDataSource {
    suspend fun getDataCharacters(): Response<Data>
    suspend fun getDataCharactersByPage(page: Int): Response<Data>
    suspend fun getDataEpisodes(): Response<Data>
    suspend fun getDataEpisodesByPage(page: Int): Response<Data>
    suspend fun getDataLocations(): Response<Data>
    suspend fun getDataLocationsByPage(page: Int): Response<Data>
}