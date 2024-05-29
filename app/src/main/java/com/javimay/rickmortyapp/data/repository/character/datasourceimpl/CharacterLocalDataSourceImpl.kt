package com.javimay.rickmortyapp.data.repository.character.datasourceimpl

import com.javimay.rickmortyapp.data.db.daos.ICharacterDao
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterLocalDataSourceImpl @Inject constructor(
    private val characterDao: ICharacterDao
) : ICharacterLocalDataSource {
    override suspend fun getCharactersFromDb(): List<Character> =
        characterDao.getCharacters()
    override suspend fun getCharacterFromDb(characterId: Long): Character =
        characterDao.getCharacterById(characterId)

    override suspend fun getCharacterWithEpisodeFromDb(characterWithEpisodeId: Long): CharacterWithEpisode =
        characterDao.getCharacterWithEpisodeById(characterWithEpisodeId)

    override suspend fun saveCharacterWithEpisodeToDb(
        characterWithEpisode: CharacterEpisodeCrossRef): Long =
        characterDao.saveCharacterWithEpisodes(characterWithEpisode)

    override suspend fun saveCharactersWithEpisodesToDb(
        characterWithEpisodeList: List<CharacterEpisodeCrossRef>): List<Long> =
        characterDao.saveCharactersWithEpisodes(characterWithEpisodeList)


    override suspend fun saveCharacterWithLocationToDb(
        characterWithLocation: CharacterLocationCrossRef): Long =
        characterDao.saveCharacterWithLocations(characterWithLocation)

    override suspend fun saveCharactersWithLocationsToDb(
        charactersWithLocations: List<CharacterLocationCrossRef>): List<Long> =
        characterDao.saveCharactersWithLocations(charactersWithLocations)


    override suspend fun saveCharactersToDb(characters: List<Character>) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.saveCharacters(characters)
        }
    }

    override suspend fun saveCharacterToDb(character: Character) {
        characterDao.saveCharacter(character)
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch { characterDao.deleteCharacters() }
    }
}