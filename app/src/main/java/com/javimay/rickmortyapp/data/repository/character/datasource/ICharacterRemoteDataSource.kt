package com.javimay.rickmortyapp.data.repository.character.datasource

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import retrofit2.Response

interface ICharacterRemoteDataSource {

    suspend fun getData(): Response<Data>
    suspend fun getCharacterById(characterId: Long): Response<Result>

    suspend fun getCharactersByIds(charactersIds: IntArray): Response<List<Result>>
    suspend fun getDataByPage(page: Int): Response<Data>
}