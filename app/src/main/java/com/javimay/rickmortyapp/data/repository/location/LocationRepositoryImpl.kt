package com.javimay.rickmortyapp.data.repository.location

import android.util.Log
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.model.ResultDto
import com.javimay.rickmortyapp.data.db.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.db.relations.LocationWithCharacter
import com.javimay.rickmortyapp.data.model.toLocation
import com.javimay.rickmortyapp.data.model.toLocationList
import com.javimay.rickmortyapp.data.repository.episode.EpisodeRepositoryImpl
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationCacheDataSource
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationLocalDataSource
import com.javimay.rickmortyapp.data.repository.location.datasource.ILocationRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.ILocationRepository
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationCacheDataSource: ILocationCacheDataSource,
    private val locationLocalDataSource: ILocationLocalDataSource,
    private val locationRemoteDataSource: ILocationRemoteDataSource
) : ILocationRepository {

    companion object {
        val TAG = LocationRepositoryImpl::class.simpleName;
    }

    override suspend fun getLocations(): List<Location> = getLocationsFromCache()

    override suspend fun getLocationsByIds(locationIdsList: List<Int>): List<Location> =
        getLocationsFromCache(locationIdsList)

    override suspend fun saveLocation(location: Location) {
        locationCacheDataSource.saveLocationToCache(location)
        locationLocalDataSource.saveLocationToDb(location)
    }

    override suspend fun saveLocationWithCharacters(locationWithCharacters: LocationWithCharacter) {
        saveLocationsWithCharactersToCache(locationWithCharacters)
    }

    private suspend fun saveLocationsWithCharactersToCache(locationWithCharacters: LocationWithCharacter) {
        locationCacheDataSource.saveLocationWithCharactersToCache(locationWithCharacters)
        locationWithCharacters.characters.forEach {
            locationLocalDataSource.saveLocationWithCharactersToDb(
                CharacterLocationCrossRef(
                    it.characterId,
                    locationWithCharacters.location.locationId
                )
            )
        }
    }

    private suspend fun getLocationsFromCache(): List<Location> {
        lateinit var locationList: List<Location>
        Log.i(TAG, "Get Locations init")
        try {
            locationList = locationCacheDataSource.getLocationsFromCache()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (locationList.isEmpty()) {
            locationList = getLocationsFromDb()
            locationCacheDataSource.saveLocationsToCache(locationList)
            Log.i(TAG, "Locations saved to Cache")
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
            locationList = getLocationsByIdsFromDb(locationIdsList)
            saveLocations(locationList)
        } else {
            val idInDb = locationList.map { it.locationId }
            val locationIdsListToSave = locationIdsList.filterNot { idInDb.contains(it.toLong()) }
            if (locationIdsListToSave.isNotEmpty()) {
                val newLocationsToSave = getLocationsByIdsFromDb(locationIdsListToSave)
                saveLocations(newLocationsToSave)
            }
        }
        return locationList
    }

    override suspend fun saveLocations(locations: List<Location>) {
        locationCacheDataSource.saveLocationsToCache(locations)
        locationLocalDataSource.saveLocationsToDb(locations)
    }

    private suspend fun getLocationsFromDb(): List<Location> {
        var locationList = mutableSetOf<Location>()
        try {
            locationList.addAll(locationLocalDataSource.getLocationsFromDb())
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (locationList.isEmpty()) {
            locationList.add(createUnknownLocation())
            locationList.addAll(getLocationsFromApi())
            locationLocalDataSource.saveLocationsToDb(locationList.toList())
            Log.i(TAG, "Locations saved to Db")
        }
        return locationList.toList()
    }

    private suspend fun getLocationsByIdsFromDb(locationsIdsList: List<Int>): List<Location> {
        var locationList = listOf<Location>()
        try {
            locationList =
                locationLocalDataSource.getLocationsByIdsFromDb(locationsIdsList.map { it.toLong() })
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (locationList.isEmpty()) {
            locationList = getLocationsByIdsFromApi(locationsIdsList)
        }
        return locationList
    }

    private suspend fun getLocationsFromApi(): List<Location> {
        val locationList = mutableSetOf<Location>()
        Log.i(TAG, "Get locations from Api init")
        try {
            val response: Response<Data> = locationRemoteDataSource.getLocations()
            val body = response.body()
            body?.let { locationData ->
                locationList.addAll(body.results.map { it.toLocation() })
                locationList.addAll(fetchAllLocations(locationData.info.pages))
                Log.i(TAG, "All locations fetched")
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return locationList.toList()
    }

    private suspend fun getLocationsByIdsFromApi(locationIdsList: List<Int>): List<Location> {
        var locationList = mutableSetOf<Location>()
        try {
            val response: Response<List<ResultDto>> =
                locationRemoteDataSource.getLocationsByIds(locationIdsList)
            val body = response.body()
            body.let { resultData ->
                resultData?.let { resultList -> locationList.addAll(resultList.map { it.toLocation() }) }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return locationList.toList()
    }

    private suspend fun fetchAllLocations(totalPages: Int) = callbackFlow {
        val locationList = mutableSetOf<Location>()
        try {
            for (page in 2..totalPages) {
                val response = locationRemoteDataSource.getLocationsByPage(page)
                response.body().let {
                    it?.results?.let { listLocationResult ->
                        locationList.addAll(listLocationResult.toLocationList())
                    }
                }
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        trySend(locationList)
        close()
    }.first()

    private fun createUnknownLocation(): Location =
        Location("", "", 0, "Unknown Location", "", "")

}