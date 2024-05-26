package com.javimay.rickmortyapp.data.db.entities

import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.utils.LOCATION_TABLE

@Entity(tableName = LOCATION_TABLE)
data class Location(
    val created: String,
    val dimension: String,
    @PrimaryKey(autoGenerate = false)
    val locationId: Long,
    val name: String,
    /*@Relation(
        parentColumn = "locationId", entityColumn = "characterId",
        associateBy = Junction(CharacterLocationCrossRef::class)
    )
    val residents: List<Character>,*/
    val type: String,
    val url: String
)
