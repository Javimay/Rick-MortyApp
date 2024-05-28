package com.javimay.rickmortyapp.domain.usecases

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.toCharacter
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import com.javimay.rickmortyapp.utils.DataType
import com.javimay.rickmortyapp.utils.getIdsFromStringList
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository,
    private val episodeRepository: IEpisodeRepository
) {
    suspend fun execute(): List<Character> {

//        return fetchAllEpisodes(DataType.Episode)
        return fetchAllCharacters(DataType.Character)
        /*val episodeIdToCharactersIdMap: MutableMap<Int, List<Int>> = mutableMapOf()
        val episodeIdToCharactersMap: MutableMap<Int, List<Character>> = mutableMapOf()


        val charactersList =characterRepository.getCharacters()
        val result = episodesData.results.map {
            val episodeIdsList = getIdsFromStringList(it.characters)
            val episodeList = episodeRepository.getEpisodesFromIds(episodeIdsList)
            //Todo: Create CharacterWithEpisode object to save
        }*/

    }

    private suspend fun fetchAllCharacters(dataType: DataType): List<Character> {
        var charactersData = characterRepository.getCharactersData()
        /*val totalPages = charactersData.info.pages
        for (page in 2..totalPages) {
            charactersData.results.forEach { result ->
                //Get the location and origin ids from the url and fetch character location from Api
                val originAndLocationIds =
                    getIdsFromStringList(listOf(result.origin.url, result.location.url))
                val locations = locationRepository.getLocationsFromIds(originAndLocationIds)
                locationRepository.saveLocations(locations)
                val locationsSaved = locationRepository.getLocations()
                //Get the episodes ids from the urls and fetch character episodes from Api

                val episodesIdsList = getIdsFromStringList(result.episode)
                val episodeList = episodeRepository.getEpisodesFromIds(episodesIdsList)
                val character = result.toCharacter(context)
                characterRepository.saveCharacter(character)
                Log.i("GetUserUseCase", "SaveCharacter: ${character.characterId}")
                //Save character and episode cross ref to Db
                episodeList.forEach {
                    characterRepository.saveCharactersWithEpisodes(
                        CharacterEpisodeCrossRef(
                            character.characterId,
                            it.episodeId
                        )
                    )
                }
            }
            charactersData = characterRepository.getCharactersData(page)
        }*/
        return characterRepository.getCharacters()
    }

    /*private suspend fun fetchAllEpisodes(dataType: DataType): Boolean {
        var episodesData = dataRepository.getData(dataType)
        val totalPages = episodesData.info.pages
        for (page in 2..totalPages) {
            episodesData.results.forEach { result ->
                val charactersIdsList = getIdsFromStringList(result.characters)
                // get episodes' Characters from Api and save it to Db
                val charactersList = fetchCharactersByIds(charactersIdsList)
                val originAndLocationIds = getLocationIdsFromCharacters(charactersList)

//                fetchLocationsByIds(originAndLocationIds)
                characterRepository.saveCharacters(charactersList)
//                episodeRepository.saveEpisodeWithCharacters(EpisodeWithCharacter(result.toEpisode(), charactersList))
            }
            episodesData = dataRepository.getData(dataType, page)
        }
        return episodeRepository.getEpisodes().isNotEmpty()
    }*/

    private fun getLocationIdsFromCharacters(charactersList: List<Character>): List<Long> {
        val locationsIdsSet = charactersList.map { it.locationId }.toSet()
        val originIdsSet = charactersList.map { it.originId }.toSet()
        return locationsIdsSet.union(originIdsSet).toList()
    }

    /* private fun getLocationIdsFromCharacter(character: Character): List<Long> {
         val locationsIdsSet = character.locationId
         val originIdsSet = character.originId
         return locationsIdsSet.union(originIdsSet).toList()
     }*/

    /*private suspend fun fetchAllLocations(dataType: DataType) {
        var locationsData = dataRepository.getData(dataType)
        val totalPages = locationsData.info.pages
        for (page in 2..totalPages) {
            locationsData.results.forEach { result ->
                val residentsIdsList = getIdsFromStringList(result.residents)
                // get locations' Characters from Api and save it to Db
                val residentsList = fetchCharactersByIds(residentsIdsList)
                locationRepository.saveLocationWithCharacters(
                    LocationWithCharacter(
                        result.toLocation(),
                        residentsList
                    )
                )
            }
            locationsData = dataRepository.getData(dataType, page)
        }
    }*/

    private suspend fun fetchCharactersByIds(charactersIdsList: List<Int>): List<Character> {
        return callbackFlow {
            trySend(characterRepository.getCharactersByIds(charactersIdsList.map { it.toLong() }))
            close()
        }.first()
    }

    private suspend fun fetchEpisodesByIds(episodesIdsList: List<Int>): List<Episode> {
        return callbackFlow {
            trySend(episodeRepository.getEpisodesFromIds(episodesIdsList))
            close()
        }.first()
    }
}