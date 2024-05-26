package com.javimay.rickmortyapp.data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode

data class EpisodeWithCharacter (
    @Embedded val episode: Episode,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val characters: List<Character>

)