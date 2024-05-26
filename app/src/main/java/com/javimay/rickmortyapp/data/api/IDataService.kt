package com.javimay.rickmortyapp.data.api

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
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

    @GET("$GET_CHARACTER/{page}")
    suspend fun getCharacterByPage(@Path("page") page: Int): Response<Data>

    @GET("$GET_CHARACTER/{characterId}")
    suspend fun getCharacterById(@Query("characterId")characterId: Long): Response<Result>

    @GET("$GET_CHARACTER/{charactersIds}")
    suspend fun getCharactersByIds(@Query("charactersIds")charactersIds: IntArray): Response<List<Result>>

    @GET(GET_EPISODES)
    suspend fun getEpisodes(): Response<Data>

    @GET("$GET_EPISODES/{page}")
    suspend fun getEpisodesByPage(@Path("page") page: Int): Response<Data>

    @GET("$GET_EPISODES/{episodeIds}")
    suspend fun getEpisodesByIds(@Query("episodeIds")episodeIds: IntArray): Response<List<Result>>

    @GET(GET_LOCATIONS)
    suspend fun getLocations(): Response<Data>

    @GET("$GET_LOCATIONS/{page}")
    suspend fun getLocationsByPage(@Path("page") page: Int): Response<Data>
    @GET("$GET_LOCATIONS/{locationIds}")
    suspend fun getLocationsByIds(@Query("locationIds")locationIds: IntArray): Response<List<Result>>
}