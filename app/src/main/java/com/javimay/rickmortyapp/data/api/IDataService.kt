package com.javimay.rickmortyapp.data.api

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.EpisodeData
import com.javimay.rickmortyapp.data.model.EpisodeResult
import com.javimay.rickmortyapp.data.model.ResultDto
import com.javimay.rickmortyapp.utils.GET_CHARACTER
import com.javimay.rickmortyapp.utils.GET_EPISODES
import com.javimay.rickmortyapp.utils.GET_LOCATIONS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataService {

    @GET(GET_CHARACTER)
    suspend fun getCharacters(): Response<Data>

    @GET(GET_CHARACTER)
    suspend fun getCharacterByPage(@Query("page") page: Int): Response<Data>

    @GET("$GET_CHARACTER/")
    suspend fun getCharacterById(@Path("characterId")characterId: Long): Response<ResultDto>

    @GET("$GET_CHARACTER/")
    suspend fun getCharactersByIds(@Path("charactersIds")charactersIds: String): Response<List<ResultDto>>

    @GET(GET_EPISODES)
    suspend fun getEpisodes(): Response<EpisodeData>

    @GET(GET_EPISODES)
    suspend fun getEpisodesByPage(@Query("page") page: Int): Response<EpisodeData>

    @GET("$GET_EPISODES/")
    suspend fun getEpisodesByIds(@Path("episodeIds")episodeIds: String): Response<List<EpisodeResult>>

    @GET(GET_LOCATIONS)
    suspend fun getLocations(): Response<Data>

    @GET(GET_LOCATIONS)
    suspend fun getLocationsByPage(@Query("page") page: Int): Response<Data>
    @GET("$GET_LOCATIONS/")
    suspend fun getLocationsByIds(@Path("locationIds")locationIds: String): Response<List<ResultDto>>
}