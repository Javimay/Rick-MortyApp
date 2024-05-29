package com.javimay.rickmortyapp.data.repository.character.datasource

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef

interface ICharacterCacheDataSource {
    suspend fun getCharactersFromCache(): List<Character>

    suspend fun getCharacterFromCache(characterId: Long): Character?

    suspend fun saveCharactersToCache(character: List<Character>)

    suspend fun saveCharactersWithEpisodesToCache(charactersWithEpisodes: List<CharacterEpisodeCrossRef>)
    suspend fun saveCharactersWithLocationsToCache(charactersWithLocations: List<CharacterLocationCrossRef>)

    suspend fun saveCharacterToCache(character: Character)
}