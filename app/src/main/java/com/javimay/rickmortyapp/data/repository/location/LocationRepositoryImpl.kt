package com.javimay.rickmortyapp.data.repository.location

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.Result
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.model.relations.LocationWithCharacter
import com.javimay.rickmortyapp.data.model.toLocation
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationCacheDataSource
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationLocalDataSource
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.ILocationRepository
import retrofit2.Response
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationCacheDataSource: ILocationCacheDataSource,
    private val locationLocalDataSource: ILocationLocalDataSource,
    private val locationRemoteDataSource: ILocationRemoteDataSource
): ILocationRepository {

    companion object {
        val TAG = LocationRepositoryImpl::class.simpleName;
    }
    override suspend fun getLocations(): List<Location> = getLocationsFromCache()

    override suspend fun getLocationsFromIds(locationIdsList: List<Int>): List<Location> =
        getLocationsFromCache(locationIdsList)

    override suspend fun saveLocationWithCharacters(locationWithCharacters: LocationWithCharacter) {
        saveLocationsWithCharactersToCache(locationWithCharacters)
    }

    private suspend fun saveLocationsWithCharactersToCache(locationWithCharacters: LocationWithCharacter) {
        locationCacheDataSource.saveLocationWithCharactersToCache(locationWithCharacters)
        locationWithCharacters.characters.forEach {
            locationLocalDataSource.saveLocationWithCharactersToDb(
                CharacterLocationCrossRef(it.characterId, locationWithCharacters.location.locationId)
            )
        }

    }

    private suspend fun getLocationsFromCache(): List<Location> {
        lateinit var locationList: List<Location>

        try {
            locationList = locationCacheDataSource.getLocationsFromCache()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (locationList.isEmpty()) {
            locationList = getLocationsFromDb()
            locationCacheDataSource.saveLocationsToCache(locationList)
        }
        return locationList
    }

    private suspend fun getLocationsFromCache(locationIdsList: List<Int>): List<Location> {
        var locationList = listOf<Location>()
        try {
            locationList = locationCacheDataSource.getLocationsByIdsFromCache(locationIdsList)
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (locationList.isEmpty()) {
            locationList = getLocationsFromDb(locationIdsList)
            locationCacheDataSource.saveLocationsToCache(locationList)
        }
        return locationList
    }

    private suspend fun getLocationsFromDb(): List<Location> {
        lateinit var locationList: List<Location>
        try {
            locationList = locationLocalDataSource.getLocationsFromDb()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            locationList = emptyList()
        }
        if (locationList.isEmpty()) {
            locationList = getLocationsFromApi()
        }
        return locationList
    }

    private suspend fun getLocationsFromDb(locationsIdsList: List<Int>): List<Location> {
        var locationList: List<Location>
        try {
            locationList =
                locationLocalDataSource.getLocationsByIdsFromDb(locationsIdsList.map { it.toLong() })
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
            locationList = emptyList()
        }
        if (locationList.isEmpty()) {
            locationList = getLocationsFromApi(locationsIdsList)
        }
        return locationList
    }

    private suspend fun getLocationsFromApi(): List<Location> {
        lateinit var locationList: List<Location>
        try {
            val response: Response<Data> = locationRemoteDataSource.getData()
            val body = response.body()
            if (body != null) {
                locationList = body.results.map { it.toLocation() }

            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return locationList
    }

    private suspend fun getLocationsFromApi(locationIdsList: List<Int>): List<Location> {
        lateinit var locationList: List<Location>
        try {
            val response: Response<List<Result>> =
                locationRemoteDataSource.getDataByIds(locationIdsList)
            val body = response.body()
            if (body != null) {
                locationList = body.map { it.toLocation() }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return locationList
    }
}