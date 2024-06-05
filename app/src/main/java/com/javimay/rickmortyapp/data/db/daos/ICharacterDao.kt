package com.javimay.rickmortyapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.db.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.db.relations.CharacterWithEpisode
import com.javimay.rickmortyapp.utils.CHARACTER_TABLE

@Dao
interface ICharacterDao {

    @Query("SELECT * FROM $CHARACTER_TABLE")
    suspend fun getCharacters(): List<Character>

    @Query("SELECT * FROM $CHARACTER_TABLE WHERE characterId = :characterId")
    suspend fun getCharacterById(characterId: Long): Character

    @Transaction
    @Query("SELECT * FROM $CHARACTER_TABLE WHERE characterId = :characterWithEpisodeId")
    suspend fun getCharacterWithEpisodeById(characterWithEpisodeId: Long): CharacterWithEpisode

    @Transaction
    @Query("SELECT * FROM $CHARACTER_TABLE")
    fun getCharactersWithEpisodes(): List<CharacterWithEpisode>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCharacters(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCharacter(character: Character)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacterWithEpisodes(
        characterWithEpisode: CharacterEpisodeCrossRef
    ) : Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharactersWithEpisodes(
        characterWithEpisode: List<CharacterEpisodeCrossRef>): List<Long>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacterWithLocations(
        characterWithLocation: CharacterLocationCrossRef
    ): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharactersWithLocations(
        charactersWithLocation: List<CharacterLocationCrossRef>): List<Long>

    @Query("DELETE FROM $CHARACTER_TABLE")
    suspend fun deleteCharacters()
}