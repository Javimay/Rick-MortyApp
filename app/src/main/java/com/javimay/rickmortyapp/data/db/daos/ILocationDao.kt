package com.javimay.rickmortyapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.model.relations.EpisodeWithCharacter
import com.javimay.rickmortyapp.data.model.relations.LocationWithCharacter
import com.javimay.rickmortyapp.utils.EPISODE_TABLE
import com.javimay.rickmortyapp.utils.LOCATION_TABLE

@Dao
interface ILocationDao {

    @Query("SELECT * FROM $LOCATION_TABLE")
    suspend fun getLocations(): List<Location>

    @Query("SELECT * FROM $LOCATION_TABLE Where locationId IN (:locationsIds)")
    suspend fun getLocationsByIds(locationsIds: List<Long>): List<Location>

    @Transaction
    @Query("SELECT * FROM $LOCATION_TABLE")
    suspend fun getLocationWithCharacters(): List<LocationWithCharacter>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveLocations(locations: List<Location>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocationWithCharacters(locationWithCharacters : CharacterLocationCrossRef)

    @Query("DELETE FROM $LOCATION_TABLE")
    suspend fun deleteLocations()
}