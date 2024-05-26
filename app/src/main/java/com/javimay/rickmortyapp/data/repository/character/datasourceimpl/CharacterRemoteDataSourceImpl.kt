package com.javimay.rickmortyapp.data.repository.character.datasourceimpl

import com.javimay.rickmortyapp.data.api.IDataService
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val dataService: IDataService
): ICharacterRemoteDataSource {
    override suspend fun getData(): Response<Data> = dataService.getCharacters()
    override suspend fun getCharacterById(characterId: Long): Response<Result> =
        dataService.getCharacterById(characterId)

    override suspend fun getCharactersByIds(charactersIds: IntArray): Response<List<Result>> =
        dataService.getCharactersByIds(charactersIds)

    override suspend fun getDataByPage(page: Int): Response<Data> =
        dataService.getCharacterByPage(page)
}




