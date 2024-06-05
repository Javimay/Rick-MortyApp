package com.javimay.rickmortyapp.data.repository.episode.datasourceImpl

import com.javimay.rickmortyapp.data.db.daos.IEpisodeDao
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.db.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.repository.episode.datasource.IEpisodeLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeLocalDataSourceImpl @Inject constructor(
    private val episodeDao: IEpisodeDao
): IEpisodeLocalDataSource {
    override suspend fun getEpisodesFromDb(): List<Episode> =
        episodeDao.getEpisodes()

    override suspend fun getEpisodesByIdsFromDb(episodeIds: List<Long>): List<Episode> =
        episodeDao.getEpisodesByIds(episodeIds)

    override suspend fun saveEpisodeToDb(episode: Episode) {
        episodeDao.saveEpisode(episode)
    }

    override suspend fun saveEpisodesToDb(episodes: List<Episode>) =
        episodeDao.saveEpisodes(episodes)

    override suspend fun getEpisodesWithCharactersFromDb(): List<EpisodeWithCharacter> =
        episodeDao.getEpisodeWithCharacters()

    override suspend fun getEpisodesWithCharactersByIdFromDb(episodeId: Long): EpisodeWithCharacter =
        episodeDao.getEpisodeWithCharacters(episodeId)

    override suspend fun saveEpisodeWithCharactersToDb(episodeWithCharacters: CharacterEpisodeCrossRef) {
        episodeDao.saveEpisodeWithCharacters(episodeWithCharacters)
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch { episodeDao.deleteEpisodes() }
    }
}