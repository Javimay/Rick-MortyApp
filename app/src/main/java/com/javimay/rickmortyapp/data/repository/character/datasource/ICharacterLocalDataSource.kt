package com.javimay.rickmortyapp.data.repository.character.datasource

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.db.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.db.relations.CharacterWithEpisode

interface ICharacterLocalDataSource {
    suspend fun getCharactersFromDb(): List<Character>

    suspend fun getCharacterFromDb(characterId: Long): Character

    suspend fun getCharacterWithEpisodeFromDb(characterWithEpisodeId: Long): CharacterWithEpisode
    suspend fun saveCharacterWithEpisodeToDb(
        characterWithEpisode: CharacterEpisodeCrossRef
    ): Long
    suspend fun saveCharactersWithEpisodesToDb(
        characterWithEpisodeList: List<CharacterEpisodeCrossRef>) : List<Long>

    suspend fun saveCharacterWithLocationToDb(
        characterWithLocation: CharacterLocationCrossRef
    ): Long

    suspend fun saveCharactersWithLocationsToDb(
        charactersWithLocations: List<CharacterLocationCrossRef>): List<Long>

    suspend fun saveCharactersToDb(characters: List<Character>)
    suspend fun saveCharacterToDb(character: Character)

    suspend fun clearAll()
}