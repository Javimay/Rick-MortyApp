package com.javimay.rickmortyapp.data.repository.character.datasource

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.ResultDto
import retrofit2.Response

interface ICharacterRemoteDataSource {

    suspend fun getData(): Response<Data>
    suspend fun getCharacterById(characterId: Long): Response<ResultDto>

    suspend fun getCharactersByIds(charactersIds: List<Int>): Response<List<ResultDto>>
    suspend fun getDataByPage(page: Int): Response<Data>
}