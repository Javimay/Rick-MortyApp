package com.javimay.rickmortyapp.data.repository.episode.datasource

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter

interface IEpisodeCacheDataSource {

    suspend fun getEpisodesFromCache(): List<Episode>
    suspend fun getEpisodeByIdFromCache(episodeId: Long): Episode?
    suspend fun getEpisodesByIdsFromCache(episodeIds: List<Long>): List<Episode>
    suspend fun getEpisodeFromCache(episodeId: Long): Episode?
    suspend fun saveEpisodesToCache(episodes: List<Episode>)
    suspend fun getEpisodesWithCharactersFromCache(): List<EpisodeWithCharacter>
    suspend fun getEpisodesWithCharactersByIdFromCache(episodeId: Int): EpisodeWithCharacter?
    suspend fun saveEpisodeToCache(episode: Episode)
    suspend fun saveEpisodeWithCharactersToCache(episodeWithCharacters: EpisodeWithCharacter)
}