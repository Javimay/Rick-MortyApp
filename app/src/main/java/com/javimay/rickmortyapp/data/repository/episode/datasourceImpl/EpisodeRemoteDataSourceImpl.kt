package com.javimay.rickmortyapp.data.repository.episode.datasourceImpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class EpisodeRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
): IEpisodeRemoteDataSource {
    override suspend fun getData(): Response<Data> = dataService.getEpisodes()
    override suspend fun getDataByIds(episodeIds: List<Int>): Response<List<Result>> =
        dataService.getEpisodesByIds(episodeIds.toIntArray())

    override suspend fun getDataByPage(page: Int): Response<Data> =
        dataService.getCharacterByPage(page)
}