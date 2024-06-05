package com.javimay.rickmortyapp.domain.usecases

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.db.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.repository.character.CharacterRepositoryImpl
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import com.javimay.rickmortyapp.utils.getIdFromString
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getCharacterUseCase: GetCharacterUseCase
) {
    companion object{
        private val TAG = GetDataUseCase::class.java.simpleName
    }

    suspend fun execute(): Boolean {
        val episodeList = getEpisodesUseCase.execute()
        val locationsList = getLocationUseCase.execute()
        val charactersList = getCharacterUseCase.execute()
        Log.i(TAG, "Data Saved")
        val fetchEpisodesDone = episodeList.isNotEmpty()
        val fetchLocationsDone = locationsList.isNotEmpty()
        val fetchCharactersDone = charactersList.isNotEmpty()

        val isSavedData =
            if (fetchEpisodesDone && fetchLocationsDone && fetchCharactersDone) {
                fetchEpisodesWithLocationsAndCharacters(charactersList)
            } else {
                false
            }
        Log.i(TAG, "CrossRef Data Saved: $isSavedData")
        return isSavedData
    }

    private suspend fun fetchEpisodesWithLocationsAndCharacters(
        charactersList: List<Character>
    ): Boolean {
        val characterAndLocationList = mutableListOf<CharacterLocationCrossRef>()
        val characterAndEpisodesList = mutableListOf<CharacterEpisodeCrossRef>()

        characterAndLocationList.addAll(charactersList.map { character ->
            CharacterLocationCrossRef(character.characterId, character.locationId)
        })
        //Create a list of CharacterEpisodeCrossRef
        charactersList.forEach { character ->
            character.episodeList.forEach {
                characterAndEpisodesList.add(
                    CharacterEpisodeCrossRef(character.characterId, getIdFromString(it).toLong()
                    )
                )
            }
        }

        return characterRepository.saveCharactersWithLocationsList(characterAndLocationList) &&
            characterRepository.saveCharactersWithEpisodesList(characterAndEpisodesList)
    }
}