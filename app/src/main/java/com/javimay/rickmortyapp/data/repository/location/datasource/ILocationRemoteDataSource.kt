package com.javimay.rickmortyapp.data.repository.location.datasource

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import retrofit2.Response

interface ILocationRemoteDataSource {
    suspend fun getData(): Response<Data>
    suspend fun getDataByIds(episodeIds: List<Int>): Response<List<Result>>
    suspend fun getDataByPage(page: Int): Response<Data>
}