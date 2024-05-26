package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.model.relations.LocationWithCharacter
import com.javimay.rickmortyapp.data.model.toEpisode
import com.javimay.rickmortyapp.data.model.toLocation
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import com.javimay.rickmortyapp.domain.repository.IDataRepository
import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import com.javimay.rickmortyapp.domain.repository.ILocationRepository
import com.javimay.rickmortyapp.utils.DataType
import com.javimay.rickmortyapp.utils.getIdsFromStringList
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val dataRepository: IDataRepository,
    private val characterRepository: ICharacterRepository,
    private val episodeRepository: IEpisodeRepository,
    private val locationRepository: ILocationRepository
) {
    suspend fun execute() : Boolean {
        return fetchAllEpisodes(DataType.Episode)
        /*val episodeIdToCharactersIdMap: MutableMap<Int, List<Int>> = mutableMapOf()
        val episodeIdToCharactersMap: MutableMap<Int, List<Character>> = mutableMapOf()


        val charactersList =characterRepository.getCharacters()
        val result = episodesData.results.map {
            val episodeIdsList = getIdsFromStringList(it.characters)
            val episodeList = episodeRepository.getEpisodesFromIds(episodeIdsList)
            //Todo: Create CharacterWithEpisode object to save
        }*/

    }
    private suspend fun fetchAllEpisodes(dataType: DataType): Boolean {
        var episodesData = dataRepository.getData(dataType)
        val totalPages = episodesData.info.pages
        for (page in 2..totalPages) {
            episodesData.results.forEach { result ->
                val charactersIdsList = getIdsFromStringList(result.characters)
                // get episodes' Characters from Api and save it to Db
                val charactersList = fetchCharactersByIds(charactersIdsList)
                episodeRepository.saveEpisodeWithCharacters(EpisodeWithCharacter(result.toEpisode(), charactersList))
            }
            episodesData = dataRepository.getData(dataType, page)
        }
        return episodeRepository.getEpisodes().isNotEmpty()
    }

    private suspend fun fetchAllLocations(dataType: DataType) {
        var locationsData = dataRepository.getData(dataType)
        val totalPages = locationsData.info.pages
        for (page in 2..totalPages) {
            locationsData.results.forEach { result ->
                val residentsIdsList = getIdsFromStringList(result.residents)
                // get locations' Characters from Api and save it to Db
                val residentsList = fetchCharactersByIds(residentsIdsList)
                locationRepository.saveLocationWithCharacters(LocationWithCharacter(result.toLocation(), residentsList))
            }
            locationsData = dataRepository.getData(dataType, page)
        }
    }

    private suspend fun fetchCharactersByIds(charactersIdsList: List<Int>): List<Character>{
        return callbackFlow {
            trySend(characterRepository.getCharactersByIds(charactersIdsList.map { it.toLong() }))
            close()
        }.first()
    }

}