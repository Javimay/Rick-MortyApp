package com.javimay.rickmortyapp.data.model.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "episodeId"])
data class CharacterEpisodeCrossRef(
    @ColumnInfo(index = true)
    val characterId: Long,
    @ColumnInfo(index = true)
    val episodeId: Long
)
