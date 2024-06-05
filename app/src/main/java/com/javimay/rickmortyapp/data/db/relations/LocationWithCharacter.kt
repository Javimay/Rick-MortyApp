package com.javimay.rickmortyapp.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Location

data class LocationWithCharacter(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "locationId", entityColumn = "characterId",
        associateBy = Junction(CharacterLocationCrossRef::class)
    )
    val characters: List<Character>
)
