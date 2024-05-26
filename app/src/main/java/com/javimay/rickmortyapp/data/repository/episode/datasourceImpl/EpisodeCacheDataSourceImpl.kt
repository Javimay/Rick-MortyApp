package com.javimay.rickmortyapp.data.repository.episode.datasourceImpl

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeCacheDataSource
import javax.inject.Inject

class EpisodeCacheDataSourceImpl @Inject constructor(): IEpisodeCacheDataSource {

    private val episodeList = mutableListOf<Episode>()
    private val episodesWithCharactersList = mutableListOf<EpisodeWithCharacter>()

    override suspend fun getEpisodesFromCache(): List<Episode> = episodeList

    override suspend fun getEpisodesByIdsFromCache(episodeIdsList: List<Int>): List<Episode> =
        episodeList.filter{ episode -> episode.episodeId in episodeIdsList.map { it.toLong() } }


    override suspend fun getEpisodeFromCache(episodeId: Long): Episode? =
        episodeList.find { it.episodeId == episodeId }

    override suspend fun saveEpisodesToCache(episodes: List<Episode>) {
        episodeList.clear()
        episodeList.addAll(episodes)
    }

    override suspend fun getEpisodesWithCharactersFromCache(): List<EpisodeWithCharacter> =
        episodesWithCharactersList

    override suspend fun getEpisodesWithCharactersByIdFromCache(episodeId: Int): EpisodeWithCharacter? =
        episodesWithCharactersList.getOrNull(episodeId)

    override suspend fun saveEpisodeWithCharactersToCache(episodeWithCharacters: EpisodeWithCharacter) {
        episodesWithCharactersList.add(episodeWithCharacters)
    }

    override suspend fun saveEpisodeToCache(episode: Episode) {
        episodeList.clear()
        episodeList.add(episode)
    }

}