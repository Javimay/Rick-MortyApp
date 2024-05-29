package com.javimay.rickmortyapp.domain.repository

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef

interface ICharacterRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun getCharacterById(characterId: Long): Character
    suspend fun getCharactersByIds(charactersIds: List<Long>): List<Character>
    suspend fun saveCharacters(charactersList: List<Character>)
    suspend fun saveCharacter(character: Character)
    suspend fun saveCharactersWithEpisodesList(
        characterWithEpisodesList: List<CharacterEpisodeCrossRef>
    ) : Boolean

    suspend fun saveCharactersWithLocationsList(
        characterWithLocationsList: List<CharacterLocationCrossRef>
    ) : Boolean
}