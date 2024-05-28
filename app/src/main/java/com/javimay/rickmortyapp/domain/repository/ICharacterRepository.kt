package com.javimay.rickmortyapp.domain.repository

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef

interface ICharacterRepository {
    suspend fun getCharactersData(page: Int? = null): Data
    suspend fun getCharacters(): List<Character>

    suspend fun getCharacterById(characterId: Long): Character

    suspend fun getCharactersByIds(charactersIds: List<Long>): List<Character>
    suspend fun saveCharacters(charactersList: List<Character>)
    suspend fun saveCharacter(character: Character)
    suspend fun saveCharactersWithEpisodes(characterWithEpisodes: CharacterEpisodeCrossRef)
}