package com.javimay.rickmortyapp.data.repository.episode.datasourceImpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.EpisodeData
import com.javimay.rickmortyapp.data.model.EpisodeResult
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class EpisodeRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
): IEpisodeRemoteDataSource {
    override suspend fun getEpisodesData(): Response<EpisodeData> = dataService.getEpisodes()
    override suspend fun getEpisodesByIds(episodeIds: String): Response<List<EpisodeResult>> =
        dataService.getEpisodesByIds(episodeIds)

    override suspend fun getEpisodesByPage(page: Int): Response<EpisodeData> =
        dataService.getEpisodesByPage(page)
}