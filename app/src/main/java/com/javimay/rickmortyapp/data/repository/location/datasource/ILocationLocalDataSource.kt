package com.javimay.rickmortyapp.data.repository.location.datasource

import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.db.relations.CharacterLocationCrossRef


interface ILocationLocalDataSource {
    suspend fun getLocationsFromDb(): List<Location>

    suspend fun getLocationsByIdsFromDb(locationsIds: List<Long>): List<Location>

    suspend fun saveLocationToDb(location: Location)

    suspend fun saveLocationsToDb(locations: List<Location>)

    suspend fun saveLocationWithCharactersToDb(locationWithCharacters: CharacterLocationCrossRef)

    suspend fun clearAll()
}