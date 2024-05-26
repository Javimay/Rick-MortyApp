package com.javimay.rickmortyapp.data.db.entities

import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.utils.EPISODE_TABLE

@Entity(tableName = EPISODE_TABLE)
data class Episode(
    val airDate: String,
    /*@Relation(
        parentColumn = "episodeId", entityColumn = "characterId"*//*,
        associateBy = Junction(CharacterEpisodeCrossRef::class)*//*
    )
    val characters: List<Character>,*/
    val created: String,
    val episode: String,
    @PrimaryKey(autoGenerate = false)
    val episodeId: Long,
    val name: String,
    val url: String
)
