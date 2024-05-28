package com.javimay.rickmortyapp.domain.repository

import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.relations.LocationWithCharacter

interface ILocationRepository {

    suspend fun getLocations(): List<Location>
    suspend fun getLocationsFromIds(locationIdsList: List<Int>): List<Location>

    suspend fun saveLocation(location: Location)
    suspend fun saveLocations(locations: List<Location>)
    suspend fun saveLocationWithCharacters(locationWithCharacters: LocationWithCharacter)
}