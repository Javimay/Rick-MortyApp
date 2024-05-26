package com.javimay.rickmortyapp.data.repository.data.datasourceimpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.repository.data.datasource.IDataRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class DataRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
) : IDataRemoteDataSource {
    override suspend fun getDataCharacters(): Response<Data> =
        dataService.getCharacters()
    override suspend fun getDataCharactersByPage(page: Int): Response<Data> =
        dataService.getCharacterByPage(page)

    override suspend fun getDataEpisodes(): Response<Data> =
        dataService.getEpisodes()

    override suspend fun getDataEpisodesByPage(page: Int): Response<Data> =
        dataService.getEpisodesByPage(page)

    override suspend fun getDataLocations(): Response<Data> =
        dataService.getLocations()

    override suspend fun getDataLocationsByPage(page: Int): Response<Data> =
        dataService.getLocationsByPage(page)
}