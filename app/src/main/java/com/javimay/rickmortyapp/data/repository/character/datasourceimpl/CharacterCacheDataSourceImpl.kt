package com.javimay.rickmortyapp.data.repository.character.datasourceimpl

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterCacheDataSource
import javax.inject.Inject

class CharacterCacheDataSourceImpl @Inject constructor() : ICharacterCacheDataSource {

    private var characterList = mutableListOf<Character>()
    private var characterWithEpisodesList = mutableListOf<CharacterEpisodeCrossRef>()

    override suspend fun getCharactersFromCache(): List<Character> = characterList

    override suspend fun getCharacterFromCache(characterId: Long): Character? =
        characterList.find { it.characterId == characterId }


    override suspend fun saveCharactersToCache(character: List<Character>) {
        characterList.clear()
        characterList.addAll(character)
    }

    override suspend fun saveCharactersWithEpisodesToCache(characterWithEpisodes: CharacterEpisodeCrossRef) {
        characterWithEpisodesList.clear()
        characterWithEpisodesList.add(characterWithEpisodes)
    }

    override suspend fun saveCharacterToCache(character: Character) {
        characterList.add(character)
    }
}