package com.javimay.rickmortyapp.data.repository.location.datasourceimpl

import com.javimay.rickmortyapp.data.db.daos.ILocationDao
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationLocalDataSourceImpl @Inject constructor(
    private val locationDao: ILocationDao
): ILocationLocalDataSource {
    override suspend fun getLocationsFromDb(): List<Location> =
        locationDao.getLocations()

    override suspend fun getLocationsByIdsFromDb(locationsIds: List<Long>): List<Location> =
        locationDao.getLocationsByIds(locationsIds)

    override suspend fun saveLocationToDb(locations: List<Location>) =
        locationDao.saveLocations(locations)

    override suspend fun saveLocationWithCharactersToDb(locationWithCharacters: CharacterLocationCrossRef) {
        locationDao.saveLocationWithCharacters(locationWithCharacters)
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch { locationDao.deleteLocations() }
    }
}