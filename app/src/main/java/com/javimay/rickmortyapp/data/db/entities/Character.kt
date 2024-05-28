package com.javimay.rickmortyapp.data.db.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.javimay.rickmortyapp.utils.CHARACTER_TABLE

@Entity(
    tableName = CHARACTER_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("locationId"), childColumns = arrayOf("locationId"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("locationId"), childColumns = arrayOf("originId"),
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Character(
    val created: String,

    /*@Relation(
        parentColumn = "characterId", entityColumn = "episodeId"*//*,
        associateBy = Junction(CharacterEpisodeCrossRef::class)*//*
    )
    val episode: List<Episode>,*/

    val gender: String,
    @PrimaryKey(autoGenerate = false)
    val characterId: Long,
    val image: Bitmap,
//    @Relation(parentColumn = "location", entityColumn = "locationId")
    @ColumnInfo(index = true)
    val locationId: Long,
    val name: String,
//    @Relation(parentColumn = "origin", entityColumn = "locationId")
    @ColumnInfo(index = true)
    val originId: Long,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
