package com.javimay.rickmortyapp.data.repository.episode.datasource

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.db.relations.EpisodeWithCharacter

interface IEpisodeLocalDataSource {

    suspend fun getEpisodesFromDb(): List<Episode>

    suspend fun getEpisodesByIdsFromDb(episodeIds: List<Long>): List<Episode>

    suspend fun saveEpisodeToDb(episode: Episode)
    suspend fun saveEpisodesToDb(episodes: List<Episode>)

    suspend fun getEpisodesWithCharactersFromDb(): List<EpisodeWithCharacter>

    suspend fun getEpisodesWithCharactersByIdFromDb(episodeId: Long): EpisodeWithCharacter
    suspend fun saveEpisodeWithCharactersToDb(episodeWithCharacters: CharacterEpisodeCrossRef)

    suspend fun clearAll()
}