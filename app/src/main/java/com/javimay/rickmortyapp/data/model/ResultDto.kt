package com.javimay.rickmortyapp.data.model

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.utils.getBitmap
import com.javimay.rickmortyapp.utils.getIdFromString
import kotlinx.serialization.SerialName

data class ResultDto(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val dimension: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Origin,
    val name: String,
    val origin: Origin,
    val residents: List<String>,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

suspend fun ResultDto.toCharacter(context: Context): Character {
    return Character(
        this.created,
        this.gender,
        this.id.toLong(),
        getBitmap(context, this.image),
        getIdFromString(this.location.url).toLong(),
        this.name,
        getIdFromString(this.origin.url).toLong(),
        this.species,
        this.status,
        this.type,
        this.url
    )
}

suspend fun List<ResultDto>.toCharacterList(context: Context): List<Character> {
    return map { it.toCharacter(context) }
}

suspend fun ResultDto.toLocation(): Location {
    return Location(
        this.created,
        this.dimension,
        this.id.toLong(),
        this.name,
        this.type,
        this.url,
    )
}
