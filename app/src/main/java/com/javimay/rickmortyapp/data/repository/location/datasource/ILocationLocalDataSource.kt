package com.javimay.rickmortyapp.data.repository.location.datasource

import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.model.relations.LocationWithCharacter


interface ILocationLocalDataSource {
    suspend fun getLocationsFromDb(): List<Location>

    suspend fun getLocationsByIdsFromDb(locationsIds: List<Long>): List<Location>

    suspend fun saveLocationToDb(locations: List<Location>)

    suspend fun saveLocationWithCharactersToDb(locationWithCharacters: CharacterLocationCrossRef)

    suspend fun clearAll()
}