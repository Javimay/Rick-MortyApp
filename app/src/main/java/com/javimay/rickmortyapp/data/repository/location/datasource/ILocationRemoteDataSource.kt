package com.javimay.rickmortyapp.data.repository.location.datasource

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.ResultDto
import retrofit2.Response

interface ILocationRemoteDataSource {
    suspend fun getLocations(): Response<Data>
    suspend fun getLocationsByIds(locationsIds: List<Int>): Response<List<ResultDto>>
    suspend fun getLocationsByPage(page: Int): Response<Data>
}