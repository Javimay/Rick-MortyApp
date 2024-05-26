package com.javimay.rickmortyapp.domain.repository

import com.javimay.rickmortyapp.data.db.entities.Character

interface ICharacterRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun getCharacterById(characterId: Long): Character

    suspend fun getCharactersByIds(charactersIds: List<Long>): List<Character>
}