package com.javimay.rickmortyapp.data.repository.location.datasourceimpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.ResultDto
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
): ILocationRemoteDataSource {
    override suspend fun getLocations(): Response<Data> = dataService.getLocations()

    override suspend fun getLocationsByIds(locationsIds: List<Int>): Response<List<ResultDto>> =
        dataService.getLocationsByIds(locationsIds.joinToString(","))

    override suspend fun getLocationsByPage(page: Int): Response<Data> =
        dataService.getCharacterByPage(page)
}