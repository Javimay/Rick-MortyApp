package com.javimay.rickmortyapp.data.repository.location.datasource

import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.db.relations.LocationWithCharacter

interface ILocationCacheDataSource {

    suspend fun getLocationsFromCache(): List<Location>
    suspend fun getLocationsByIdsFromCache(locationsList: List<Int>): List<Location>
    suspend fun getLocationFromCache(locationId: Long): Location?

    suspend fun saveLocationsToCache(locations: List<Location>)
    suspend fun saveLocationToCache(location: Location)

    suspend fun saveLocationWithCharactersToCache(locationWithCharacters: LocationWithCharacter)
}