package com.javimay.rickmortyapp.data.model

import android.content.Context
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode

data class EpisodeResult(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

suspend fun EpisodeResult.toEpisodeEntity(): Episode {
    return Episode(
        this.airDate,
        this.created,
        this.episode,
        this.id.toLong(),
        this.name,
        this.url,
    )
}

suspend fun List<EpisodeResult>.toEpisodeEntityList(): List<Episode> {
    return map { it.toEpisodeEntity() }
}
