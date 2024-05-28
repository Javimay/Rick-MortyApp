package com.javimay.rickmortyapp.data.repository.episode

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.EpisodeData
import com.javimay.rickmortyapp.data.model.EpisodeResult
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.model.toEpisodeEntity
import com.javimay.rickmortyapp.data.model.toEpisodeEntityList
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

    override suspend fun getEpisodesData(): EpisodeData = getEpisodesDataFromApi()

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
        } else {
            val idInDb = episodeList.map { it.episodeId }
            val locationIdsListToSave = episodeIdsList.filterNot { idInDb.contains(it.toLong()) }
            if (locationIdsListToSave.isNotEmpty()){
                getEpisodesFromDb(locationIdsListToSave)
            }
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
            saveEpisodes(episodeList)
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
            val response: Response<List<EpisodeResult>> =
                episodeRemoteDataSource.getEpisodesByIds(episodeIdsList.joinToString(","))
            val body = response.body()
            if (body != null) {
                episodeList = body.map { it.toEpisodeEntity() }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            episodeList = emptyList()
        }
        return episodeList
    }

    private suspend fun getEpisodesFromApi(): List<Episode> {
        lateinit var episodeList: List<Episode>
        try {
            val response: Response<EpisodeData> = episodeRemoteDataSource.getEpisodesData()
            val body = response.body()
            if (body != null) {
                episodeList = body.results.map { it.toEpisodeEntity() }
                saveEpisodes(episodeList)
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return episodeList
    }

    private suspend fun getEpisodesDataFromApi(): EpisodeData {
        lateinit var episodeData: EpisodeData
        try {
            val response: Response<EpisodeData> = episodeRemoteDataSource.getEpisodesData()
            val body = response.body()
            if (body != null) {
                episodeData = body
                saveEpisodes(body.results.toEpisodeEntityList())
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return episodeData
    }

    override suspend fun saveEpisodes(episodes: List<Episode>) {
        episodeCacheDataSource.saveEpisodesToCache(episodes)
        episodeLocalDataSource.saveEpisodesToDb(episodes)
    }

    override suspend fun saveEpisode(episode: Episode) {
        episodeCacheDataSource.saveEpisodeToCache(episode)
        episodeLocalDataSource.saveEpisodeToDb(episode)
    }
}