package com.javimay.rickmortyapp.data.repository.episode

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.model.toEpisode
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeCacheDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeLocalDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import retrofit2.Response
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val episodeCacheDataSource: IEpisodeCacheDataSource,
    private val episodeLocalDataSource: IEpisodeLocalDataSource,
    private val episodeRemoteDataSource: IEpisodeRemoteDataSource
) : IEpisodeRepository {

    companion object {
        val TAG = EpisodeRepositoryImpl::class.simpleName;
    }

    override suspend fun getEpisodesData(): Data = getEpisodesDataFromApi()

    override suspend fun getEpisodes(): List<Episode> = getEpisodesFromCache()

    override suspend fun getEpisodesFromIds(episodeIdsList: List<Int>): List<Episode> =
        getEpisodesFromCache(episodeIdsList)

    override suspend fun saveEpisodeWithCharacters(episodeWithCharacters: EpisodeWithCharacter) {
        saveEpisodeWithCharactersToCache(episodeWithCharacters)
    }


    private suspend fun saveEpisodeWithCharactersToCache(episodeWithCharacters: EpisodeWithCharacter) {
        episodeCacheDataSource.saveEpisodeWithCharactersToCache(episodeWithCharacters)
        episodeWithCharacters.characters.forEach {
            episodeLocalDataSource.saveEpisodeWithCharactersToDb(
                CharacterEpisodeCrossRef(it.characterId, episodeWithCharacters.episode.episodeId)
            )
        }
    }

    override suspend fun saveEpisodes(episodes: List<Episode>) =
        episodeCacheDataSource.saveEpisodesToCache(episodes)

    private suspend fun getEpisodesFromCache(episodeIdsList: List<Int>): List<Episode> {
        var episodeList = listOf<Episode>()
        try {
            episodeList = episodeCacheDataSource.getEpisodesByIdsFromCache(episodeIdsList)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (episodeList.isEmpty()) {
            episodeList = getEpisodesFromDb(episodeIdsList)
            episodeCacheDataSource.saveEpisodesToCache(episodeList)
        }
        return episodeList
    }


     private suspend fun getEpisodesFromCache(): List<Episode> {
         lateinit var episodeList: List<Episode>

         try {
             episodeList = episodeCacheDataSource.getEpisodesFromCache()
         } catch (exception: Exception) {
             Log.i(TAG, exception.message.toString())
         }
         if (episodeList.isEmpty()) {
             episodeList = getEpisodesFromDb()
             episodeCacheDataSource.saveEpisodesToCache(episodeList)
         }
         return episodeList
     }

    private suspend fun getEpisodesFromDb(): List<Episode> {
        lateinit var episodeList: List<Episode>
        try {
            episodeList = episodeLocalDataSource.getEpisodesFromDb()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            episodeList = emptyList()
        }
        if (episodeList.isEmpty()) {
            episodeList = getEpisodesFromApi()
        }
        return episodeList
    }

    private suspend fun getEpisodesFromDb(episodeIdsList: List<Int>): List<Episode> {
        var episodeList: List<Episode>
        try {
            episodeList =
                episodeLocalDataSource.getEpisodesByIdsFromDb(episodeIdsList.map { it.toLong() })
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            episodeList = emptyList()
        }
        if (episodeList.isEmpty()) {
            episodeList = getEpisodesFromApi(episodeIdsList)
        }
        return episodeList
    }

    /*private suspend fun getCharacterFromDb(characterId: Long): CharacterWithEpisode {
        var character: CharacterWithEpisode?
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
    }*/

    private suspend fun getEpisodesFromApi(episodeIdsList: List<Int>): List<Episode> {
        lateinit var episodeList: List<Episode>
        try {
            val response: Response<List<Result>> =
                episodeRemoteDataSource.getDataByIds(episodeIdsList)
            val body = response.body()
            if (body != null) {
                episodeList = body.map { it.toEpisode() }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return episodeList
    }

    private suspend fun getEpisodesFromApi(): List<Episode> {
        lateinit var episodeList: List<Episode>
        try {
            val response: Response<Data> = episodeRemoteDataSource.getData()
            val body = response.body()
            if (body != null) {
                episodeList = body.results.map { it.toEpisode() }

            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return episodeList
    }

    private suspend fun getEpisodesDataFromApi(): Data {
        lateinit var episodeData: Data
        try {
            val response: Response<Data> = episodeRemoteDataSource.getData()
            val body = response.body()
            if (body != null) {
                episodeData = body

            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return episodeData
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