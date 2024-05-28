package com.javimay.rickmortyapp.data.repository.character.datasourceimpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.ResultDto
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
): ICharacterRemoteDataSource {
    override suspend fun getData(): Response<Data> = dataService.getCharacters()
    override suspend fun getCharacterById(characterId: Long): Response<ResultDto> =
        dataService.getCharacterById(characterId)

    override suspend fun getCharactersByIds(charactersIds: List<Int>): Response<List<ResultDto>> =
        dataService.getCharactersByIds(charactersIds.joinToString(","))

    override suspend fun getDataByPage(page: Int): Response<Data> =
        dataService.getCharacterByPage(page)
}




