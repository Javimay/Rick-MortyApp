package com.javimay.rickmortyapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.utils.EPISODE_TABLE

@Dao
interface IEpisodeDao {

    @Query("SELECT * FROM $EPISODE_TABLE")
    suspend fun getEpisodes(): List<Episode>

    @Query("SELECT * FROM $EPISODE_TABLE Where episodeId IN (:episodeIds)")
    suspend fun getEpisodesByIds(episodeIds: List<Long>): List<Episode>

    @Transaction
    @Query("SELECT * FROM $EPISODE_TABLE")
    suspend fun getEpisodeWithCharacters(): List<EpisodeWithCharacter>

    @Transaction
    @Query("SELECT * FROM $EPISODE_TABLE WHERE episodeId = :episodeId")
    suspend fun getEpisodeWithCharacters(episodeId: Long): EpisodeWithCharacter

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEpisodeWithCharacters(episodeWithCharacters : CharacterEpisodeCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEpisodes(episodes: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEpisode(episodes: Episode)

    @Query( "DELETE FROM $EPISODE_TABLE")
    suspend fun deleteEpisodes()
}