package com.javimay.rickmortyapp.domain.repository

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.EpisodeData
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter

interface IEpisodeRepository {
    suspend fun getEpisodesData(): EpisodeData
    suspend fun getEpisodes(): List<Episode>
    suspend fun getEpisodesFromIds(episodeIdsList: List<Int>): List<Episode>

    suspend fun saveEpisodeWithCharacters(episodeWithCharacters: EpisodeWithCharacter)

    suspend fun saveEpisodes(episodes: List<Episode>)
    suspend fun saveEpisode(episode: Episode)
}