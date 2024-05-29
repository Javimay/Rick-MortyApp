package com.javimay.rickmortyapp.data.repository.character

import android.content.Context
import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.ResultDto
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode
import com.javimay.rickmortyapp.data.model.toCharacter
import com.javimay.rickmortyapp.data.model.toCharacterList
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterCacheDataSource
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterLocalDataSource
import com.javimay.rickmortyapp.data.repository.character.datasource.ICharacterRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import com.javimay.rickmortyapp.utils.MAX_PAGE_SIZE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
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
        return characterList
    }

    override suspend fun saveCharacters(charactersList: List<Character>) {
        characterCacheDataSource.saveCharactersToCache(charactersList)
        characterLocalDataSource.saveCharactersToDb(charactersList)
    }

    override suspend fun saveCharacter(character: Character) {
        characterCacheDataSource.saveCharacterToCache(character)
        characterLocalDataSource.saveCharacterToDb(character)
    }

    override suspend fun saveCharactersWithEpisodesList(
        characterWithEpisodesList: List<CharacterEpisodeCrossRef>): Boolean {
        characterCacheDataSource.saveCharactersWithEpisodesToCache(characterWithEpisodesList)
        val inserted = characterLocalDataSource.saveCharactersWithEpisodesToDb(characterWithEpisodesList)
        return inserted.isNotEmpty()
    }

    override suspend fun saveCharactersWithLocationsList(
        characterWithLocationsList: List<CharacterLocationCrossRef>): Boolean {
            characterCacheDataSource.saveCharactersWithLocationsToCache(characterWithLocationsList)
            val inserted = characterLocalDataSource.saveCharactersWithLocationsToDb(characterWithLocationsList)
            return inserted.isNotEmpty()
    }

    private suspend fun getCharactersByIdsFromApi(charactersIds: List<Long>): List<Character> {
        var characterList = listOf<Character>()
        try {
            val response =
                characterRemoteDataSource.getCharactersByIds(charactersIds.map { it.toInt() })
            characterList = response.body()?.toCharacterList(context) ?: emptyList()
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
            character = getCharacterByIdFromDb(characterId)
            characterCacheDataSource.saveCharacterToCache(character)
        }
        return character
    }


    private suspend fun getCharactersFromCache(): List<Character> {
        lateinit var characterList: List<Character>
        Log.i(TAG, "Get Characters init")
        try {
            characterList = characterCacheDataSource.getCharactersFromCache()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (characterList.isEmpty()) {
            characterList = getCharactersFromDb()
            characterCacheDataSource.saveCharactersToCache(characterList)
            Log.i(TAG, "Characters saved to Cache")
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
            characterLocalDataSource.saveCharactersToDb(characterList)
            Log.i(TAG, "Characters saved to Db")
        }
        return characterList
    }

    private suspend fun getCharacterByIdFromDb(characterId: Long): Character {
        var character: Character?
        try {
            character = characterLocalDataSource.getCharacterFromDb(characterId)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            character = null
        }
        if (character == null) {
            character = getCharacterByIdFromApi(characterId)
        }
        return character
    }

    private suspend fun getCharacterWithEpisodeFromDb(characterWithEpisodeId: Long): CharacterWithEpisode? {
        var characterWithEpisode: CharacterWithEpisode?
        try {
            characterWithEpisode =
                characterLocalDataSource.getCharacterWithEpisodeFromDb(characterWithEpisodeId)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            characterWithEpisode = null
        }

        return characterWithEpisode
    }

    private suspend fun getCharactersFromApi(): List<Character> {
        val characterList = mutableSetOf<Character>()
        Log.i(TAG, "get Characters from Api init")
        try {
            val response: Response<Data> = characterRemoteDataSource.getCharacters()
            val body = response.body()
            body?.let { characterData ->
                characterList.addAll(body.results.map { it.toCharacter(context) })
                characterList.addAll(characterData.results.map { it.toCharacter(context) })
                //Iterate all pages to get Character Data
                characterList.addAll(fetchAllCharacters())
                Log.i(TAG,"characters fetched: ${characterList.size}")
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return characterList.toList()
    }

    private suspend fun getCharacterByIdFromApi(characterId: Long): Character {
        lateinit var character: Character
        try {
            val response: Response<ResultDto> =
                characterRemoteDataSource.getCharacterById(characterId)
            val body = response.body()
            if (body != null) {
                character = body.toCharacter(context)
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return character
    }

    private suspend fun fetchAllCharacters() = callbackFlow {
        val characterList = mutableSetOf<Character>()
        try {
            for (page in 2..MAX_PAGE_SIZE) {
                val response = characterRemoteDataSource.getCharactersByPage(page)
                response.body().let {
                    it?.results?.let { listCharacterResult ->
                        characterList.addAll(listCharacterResult.map { characterResult ->
                            characterResult.toCharacter(context)
                        })
                    }
                }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        trySend(characterList)
        close()
    }.first()
}