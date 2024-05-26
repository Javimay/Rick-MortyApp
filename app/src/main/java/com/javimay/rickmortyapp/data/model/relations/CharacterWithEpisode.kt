package com.javimay.rickmortyapp.data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode


data class CharacterWithEpisode(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeId",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val episodes: List<Episode>
)