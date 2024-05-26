package com.javimay.rickmortyapp.data.model

import android.content.Context
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.relations.CharacterWithEpisode
import com.javimay.rickmortyapp.utils.getBitmap


data class Result(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val dimension: String,
    val episode: String,
    val episodeList: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Location,
    val residents: List<String>,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

suspend fun Result.toCharacter(context: Context): Character {
    return Character(
        this.created,
        this.gender,
        this.id.toLong(),
        getBitmap(context, this.image),
        this.location.locationId,
        this.name,
        this.origin.locationId,
        this.species,
        this.status,
        this.type,
        this.url
    )
}

suspend fun Result.toEpisode(): Episode {
    return Episode(
        this.airDate,
        this.created,
        this.episode,
        this.id.toLong(),
        this.name,
        this.url,
    )
}

suspend fun Result.toLocation(): Location {
    return Location(
        this.created,
        this.dimension,
        this.id.toLong(),
        this.name,
        this.type,
        this.url,
    )
}

/*suspend fun Result.toCharacterWithEpisode(character: Character): CharacterWithEpisode {
    return CharacterWithEpisode(
        character
    )
}*/
