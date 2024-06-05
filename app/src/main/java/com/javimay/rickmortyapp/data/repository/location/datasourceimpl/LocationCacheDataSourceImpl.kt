package com.javimay.rickmortyapp.data.repository.location.datasourceimpl

import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.db.relations.LocationWithCharacter
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationCacheDataSource
import javax.inject.Inject

class LocationCacheDataSourceImpl@Inject constructor(): ILocationCacheDataSource {

    private val locationList = mutableListOf<Location>()
    private val locationWithCharacterList = mutableListOf<LocationWithCharacter>()

    override suspend fun getLocationsFromCache(): List<Location> = locationList

    override suspend fun getLocationsByIdsFromCache(locationsList: List<Int>): List<Location> =
        locationList.filter{ location -> location.locationId in locationsList.map { it.toLong() } }

    override suspend fun getLocationFromCache(locationId: Long): Location? =
        locationList.find { it.locationId == locationId }

    override suspend fun saveLocationsToCache(locations: List<Location>) {
        locationList.clear()
        locationList.addAll(locations)
    }

    override suspend fun saveLocationToCache(location: Location) {
        locationList.add(location)
    }

    override suspend fun saveLocationWithCharactersToCache(locationWithCharacters: LocationWithCharacter) {
        locationWithCharacterList.clear()
        locationWithCharacterList.add(locationWithCharacters)
    }

}