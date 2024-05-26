package com.javimay.rickmortyapp.data.repository.location.datasourceimpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
): ILocationRemoteDataSource {
    override suspend fun getData(): Response<Data> = dataService.getLocations()

    override suspend fun getDataByIds(locationsIds: List<Int>): Response<List<Result>> =
        dataService.getLocationsByIds(locationsIds.toIntArray())

    override suspend fun getDataByPage(page: Int): Response<Data> =
        dataService.getCharacterByPage(page)
}