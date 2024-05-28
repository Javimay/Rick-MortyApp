package com.javimay.rickmortyapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCharacterWithEpisodes(characterWithEpisode: CharacterEpisodeCrossRef)

    @Query("DELETE FROM $CHARACTER_TABLE")
    suspend fun deleteCharacters()
}