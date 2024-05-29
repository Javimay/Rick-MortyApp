package com.javimay.rickmortyapp.data.repository.episode.datasourceImpl

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeCacheDataSource
import javax.inject.Inject

class EpisodeCacheDataSourceImpl @Inject constructor() : IEpisodeCacheDataSource {

    private val episodeListCache = mutableSetOf<Episode>()
    private val episodesWithCharactersListCache = mutableSetOf<EpisodeWithCharacter>()

    override suspend fun getEpisodesFromCache(): List<Episode> = episodeListCache.toList()
    override suspend fun getEpisodeByIdFromCache(episodeId: Long): Episode? =
        episodeListCache.find { it.episodeId == episodeId }

    override suspend fun getEpisodesByIdsFromCache(episodeIds: List<Long>): List<Episode> =
        episodeListCache.filter { episode -> episodeIds.contains(episode.episodeId) }

    override suspend fun getEpisodeFromCache(episodeId: Long): Episode? =
        episodeListCache.find { it.episodeId == episodeId }

    override suspend fun saveEpisodesToCache(episodes: List<Episode>) {
        episodeListCache.addAll(episodes)
    }

    override suspend fun getEpisodesWithCharactersFromCache(): List<EpisodeWithCharacter> =
        episodesWithCharactersListCache.toList()

    override suspend fun getEpisodesWithCharactersByIdFromCache(episodeId: Int): EpisodeWithCharacter? =
        episodesWithCharactersListCache.find { it.episode.episodeId == episodeId.toLong() }

    override suspend fun saveEpisodeToCache(episode: Episode) {
        episodeListCache.add(episode)
    }
    override suspend fun saveEpisodeWithCharactersToCache(episodeWithCharacters: EpisodeWithCharacter) {
        episodesWithCharactersListCache.add(episodeWithCharacters)
    }
}