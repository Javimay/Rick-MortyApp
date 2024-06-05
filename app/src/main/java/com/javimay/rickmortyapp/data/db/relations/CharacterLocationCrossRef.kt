package com.javimay.rickmortyapp.data.db.relations

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "locationId"])
data class CharacterLocationCrossRef(
    val characterId: Long,
    val locationId: Long
)
