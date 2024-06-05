package com.javimay.rickmortyapp.data.db.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.javimay.rickmortyapp.data.model.CharacterDto
import com.javimay.rickmortyapp.data.db.relations.CharacterEpisodeCrossRef
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
    val gender: String,
    @PrimaryKey(autoGenerate = false)
    val characterId: Long,
    val image: Bitmap,
    val episodeList: List<String>,
    @ColumnInfo(index = true)
    val locationId: Long,
    val name: String,
    @ColumnInfo(index = true)
    val originId: Long,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

fun Character.toCharacterDto(): CharacterDto =
    CharacterDto(
        this.created,
        this.gender,
        this.characterId,
        this.image,
        this.episodeList,
        null,
        this.locationId,
        this.name,
        null,
        this.originId,
        this.species,
        this.status,
        this.type,
        this.url
    )
