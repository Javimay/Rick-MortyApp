package com.javimay.rickmortyapp.data.repository.character.datasource

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode

interface ICharacterLocalDataSource {
    suspend fun getCharactersFromDb(): List<Character>

    suspend fun getCharacterFromDb(characterId: Long): Character

    suspend fun getCharacterWithEpisodeFromDb(characterWithEpisodeId: Long): CharacterWithEpisode
    suspend fun saveCharacterWithEpisodeToDb(characterWithEpisode: CharacterEpisodeCrossRef)

    suspend fun saveCharactersToDb(characters: List<Character>)
    suspend fun saveCharacterToDb(character: Character)

    suspend fun clearAll()
}