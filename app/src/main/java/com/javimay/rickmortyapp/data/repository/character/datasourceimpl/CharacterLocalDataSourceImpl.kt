package com.javimay.rickmortyapp.data.repository.character.datasourceimpl

import com.javimay.rickmortyapp.data.db.daos.ICharacterDao
import com.javimay.rickmortyapp.data.db.entities.Character
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