package com.javimay.rickmortyapp.data.repository.episode

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.EpisodeData
import com.javimay.rickmortyapp.data.model.EpisodeResult
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.model.toEpisodeEntity
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeCacheDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeLocalDataSource
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
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

    override suspend fun getEpisodes(): List<Episode> = getEpisodesByIdsFromCache()

    override suspend fun getEpisodesFromIds(episodeIdsList: List<Int>): List<Episode> =
        getEpisodesByIdsFromCache(episodeIdsList)

    override suspend fun saveEpisodes(episodes: List<Episode>) {
        episodeCacheDataSource.saveEpisodesToCache(episodes)
        episodeLocalDataSource.saveEpisodesToDb(episodes)
    }

    override suspend fun saveEpisode(episode: Episode) {
        episodeCacheDataSource.saveEpisodeToCache(episode)
        episodeLocalDataSource.saveEpisodeToDb(episode)
    }

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

    private suspend fun getEpisodesByIdsFromCache(): List<Episode> {
        lateinit var episodeList: List<Episode>
        try {
            Log.i(TAG, "GetEpisodes")
            episodeList = episodeCacheDataSource.getEpisodesFromCache()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (episodeList.isEmpty()) {
            episodeList = getEpisodesByIdsFromDb()
            episodeCacheDataSource.saveEpisodesToCache(episodeList)
            Log.i(TAG, "Episodes saved to Cache")
        }
        return episodeList
    }

    private suspend fun getEpisodesByIdsFromCache(episodeIdsList: List<Int>): List<Episode> {
        var episodeList = listOf<Episode>()
        try {
            episodeList =
                episodeCacheDataSource.getEpisodesByIdsFromCache(episodeIdsList.map { it.toLong() })
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (episodeList.isEmpty()) {
            episodeList = getEpisodesByIdsFromDb(episodeIdsList)
            episodeCacheDataSource.saveEpisodesToCache(episodeList)
        } else {
            val idInDb = episodeList.map { it.episodeId }
            val locationIdsListToSave = episodeIdsList.filterNot { idInDb.contains(it.toLong()) }
            if (locationIdsListToSave.isNotEmpty()) {
                getEpisodesByIdsFromDb(locationIdsListToSave)
            }
        }
        return episodeList
    }

    private suspend fun getEpisodesByIdsFromDb(): List<Episode> {
        lateinit var episodeList: List<Episode>
        try {
            episodeList = episodeLocalDataSource.getEpisodesFromDb()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            episodeList = emptyList()
        }
        if (episodeList.isEmpty()) {
            episodeList = getEpisodesFromApi()
            episodeLocalDataSource.saveEpisodesToDb(episodeList)
            Log.i(TAG, "All episodes saved to Db")
        }
        return episodeList
    }

    private suspend fun getEpisodesByIdsFromDb(episodeIdsList: List<Int>): List<Episode> {
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
        val episodeList = mutableSetOf<Episode>()
        try {
            val response: Response<EpisodeData> = episodeRemoteDataSource.getEpisodesData()
            val body = response.body()
            body?.let { episodeData ->
                episodeList.addAll(episodeData.results.map { it.toEpisodeEntity() })
                //Iterate all pages to get Episodes Data
                episodeList.addAll(fetchAllEpisodes(episodeData.info.pages))
                Log.i(TAG, "All episodes fetched")
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return episodeList.toList()
    }

    private suspend fun fetchAllEpisodes(totalPages: Int) = callbackFlow {
        val episodeList = mutableSetOf<Episode>()
        try {
            for (page in 2..totalPages) {
                val response = episodeRemoteDataSource.getEpisodesByPage(page)
                response.body().let {
                    it?.results?.let { listEpisodeResult ->
                        episodeList.addAll(listEpisodeResult.map { episodeResult ->
                            episodeResult.toEpisodeEntity() })
                    }
                }
            }
        } catch (exception: Exception){
            Log.i(TAG, exception.message.toString())
        }
        trySend(episodeList)
        close()
    }.first()
}