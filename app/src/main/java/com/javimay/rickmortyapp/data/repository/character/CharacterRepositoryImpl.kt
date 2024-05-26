package com.javimay.rickmortyapp.data.repository.character

import android.content.Context
import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode
import com.javimay.rickmortyapp.data.model.toCharacter
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterCacheDataSource
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterLocalDataSource
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val characterCacheDataSource: ICharacterCacheDataSource,
    private val characterLocalDataSource: ICharacterLocalDataSource,
    private val characterRemoteDataSource: ICharacterRemoteDataSource
) : ICharacterRepository {

    companion object {
        val TAG = CharacterRepositoryImpl::class.simpleName;
    }

    override suspend fun getCharacters(): List<Character> = getCharactersFromCache()
    override suspend fun getCharacterById(characterId: Long): Character =
        getCharacterFromCache(characterId)

    override suspend fun getCharactersByIds(charactersIds: List<Long>): List<Character> {
        val characterList = getCharactersByIdsFromApi(charactersIds)
        characterCacheDataSource.saveCharactersToCache(characterList)
        characterLocalDataSource.saveCharactersToDb(characterList)
        return characterList
    }

    private suspend fun getCharactersByIdsFromApi(charactersIds: List<Long>): List<Character> {
        var characterList = listOf<Character>()
        try {
            val response =
                characterRemoteDataSource.getCharactersByIds(charactersIds.map { it.toInt() }
                    .toIntArray())
            characterList =
                response.body()?.let { results -> results.map { it.toCharacter(context) } }
                    ?: emptyList()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return characterList
    }

    private suspend fun getCharacterFromCache(characterId: Long): Character {
        var character: Character?
        try {
            character = characterCacheDataSource.getCharacterFromCache(characterId)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            character = null;
        }
        if (character == null) {
            character = getCharacterFromDb(characterId)
            characterCacheDataSource.saveCharacterToCache(character)
        }
        return character
    }


    private suspend fun getCharactersFromCache(): List<Character> {
        lateinit var characterList: List<Character>

        try {
            characterList = characterCacheDataSource.getCharactersFromCache()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (characterList.isEmpty()) {
            characterList = getCharactersFromDb()
            characterCacheDataSource.saveCharactersToCache(characterList)
        }
        return characterList
    }

    private suspend fun getCharactersFromDb(): List<Character> {
        lateinit var characterList: List<Character>
        try {
            characterList = characterLocalDataSource.getCharactersFromDb()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            characterList = emptyList()
        }
        if (characterList.isEmpty()) {
            characterList = getCharactersFromApi()
        }
        return characterList
    }

    private suspend fun getCharacterFromDb(characterId: Long): Character {
        var character: Character?
        try {
            character = characterLocalDataSource.getCharacterFromDb(characterId)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            character = null
        }
        if (character == null) {
            character = getCharacterFromApi(characterId)
        }
        return character
    }

    private suspend fun getCharacterWithEpisodeFromDb(characterWithEpisodeId: Long): CharacterWithEpisode? {
        var characterWithEpisode: CharacterWithEpisode?
        try {
            characterWithEpisode = characterLocalDataSource.getCharacterWithEpisodeFromDb(characterWithEpisodeId)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            characterWithEpisode = null
        }

        return characterWithEpisode
    }

    private suspend fun getCharactersFromApi(): List<Character> {
        lateinit var characterList: List<Character>
        try {
            val response: Response<Data> = characterRemoteDataSource.getData()
            val body = response.body()
            if (body != null) {
                characterList = body.results.map { it.toCharacter(context) }
                /*CoroutineScope(Dispatchers.IO).launch {
                    saveToDb(body)
                }
                totalPages = body[0].info.pages //TODO: Divide in half
                CoroutineScope(Dispatchers.IO).launch {
                    getAllCharacterPages(totalPages)
                }*/
//                characterList = body.first().results.map { it.toCharacter(context) }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return characterList
    }

    private suspend fun getCharacterFromApi(characterId: Long): Character {
        lateinit var character: Character
        try {
            val response: Response<Result> = characterRemoteDataSource.getCharacterById(characterId)
            val body = response.body()
            if (body != null) {
                character = body.toCharacter(context)
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return character
    }

    suspend fun getAllCharacterPages(totalPages: Int) {
        for (page in 2..totalPages) {
            val responsePage: Response<Data> = characterRemoteDataSource.getDataByPage(page)
            val bodyPage = responsePage.body()
            if (bodyPage != null) {
//                saveToDb(bodyPage)
            }
        }
    }

    private suspend fun saveToDb(body: List<Data>) {
        /*characterLocalDataSource.saveCharactersToDb(
            body.first().results.map { it.toCharacter(context) }
        )*/
    }

    private suspend fun saveToDb(body: Result) {
        /*characterLocalDataSource.saveCharactersToDb(
            body.first().results.map { it.toCharacter(context) }
        )*/
    }
}