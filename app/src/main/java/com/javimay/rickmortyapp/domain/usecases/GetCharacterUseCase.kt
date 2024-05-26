package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import com.javimay.rickmortyapp.domain.repository.IDataRepository
import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import com.javimay.rickmortyapp.utils.getIdsFromStringList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val dataRepository: IDataRepository,
    private val characterRepository: ICharacterRepository,
    private val episodeRepository: IEpisodeRepository
) {
    /*suspend fun execute() : List<CharacterWithEpisode> {
        *//*val dataList = dataRepository.getCharacterByPage()

        val charactersList =characterRepository.getCharacters()
        charactersList.map {
            val episodeList = getIdsFromStringList(dataList)
        }
        CoroutineScope(Dispatchers.IO).launch {
            saveToDb(body)
        }
        totalPages = body[0].info.pages //TODO: Divide in half
        CoroutineScope(Dispatchers.IO).launch {
            getAllCharacterPages(totalPages)
        }*//*
    }*/
}