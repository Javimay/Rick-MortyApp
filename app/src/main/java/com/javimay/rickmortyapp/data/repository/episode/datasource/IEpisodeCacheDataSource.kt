package com.javimay.rickmortyapp.data.repository.episode.datasource

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter

interface IEpisodeCacheDataSource {

    suspend fun getEpisodesFromCache(): List<Episode>
    suspend fun getEpisodesByIdsFromCache(episodeIdsList: List<Int>): List<Episode>
    suspend fun getEpisodeFromCache(episodeId: Long): Episode?
    suspend fun saveEpisodesToCache(episodes: List<Episode>)

    suspend fun getEpisodesWithCharactersFromCache(): List<EpisodeWithCharacter>

    suspend fun getEpisodesWithCharactersByIdFromCache(episodeId: Int): EpisodeWithCharacter?
    suspend fun saveEpisodeWithCharactersToCache(episodeWithCharacters: EpisodeWithCharacter)

    suspend fun saveEpisodeToCache(episode: Episode)
}