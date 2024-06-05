package com.javimay.rickmortyapp.data.db.entities

import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.javimay.rickmortyapp.data.model.LocationDto
import com.javimay.rickmortyapp.data.db.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.utils.LOCATION_TABLE

@Entity(tableName = LOCATION_TABLE)
data class Location(
    val created: String,
    val dimension: String?,
    @PrimaryKey(autoGenerate = false)
    val locationId: Long,
    val name: String?,
    val type: String?,
    val url: String
)

fun Location.toLocationDto(): LocationDto {
    return LocationDto(
        created,
        dimension,
        locationId,
        name,
        type,
        url
    )
}

fun List<Location>.toLocationDtoList(): List<LocationDto> {
    return map { it.toLocationDto() }
}
