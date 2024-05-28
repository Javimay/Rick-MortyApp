package com.javimay.rickmortyapp.data.repository.episode.datasource

import com.javimay.rickmortyapp.data.model.EpisodeData
import com.javimay.rickmortyapp.data.model.EpisodeResult
import retrofit2.Response

interface IEpisodeRemoteDataSource {

    suspend fun getEpisodesData(): Response<EpisodeData>
    suspend fun getEpisodesByIds(episodeIds: String): Response<List<EpisodeResult>>
    suspend fun getEpisodesByPage(page: Int): Response<EpisodeData>
}