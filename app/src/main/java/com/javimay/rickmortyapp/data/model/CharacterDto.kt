package com.javimay.rickmortyapp.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import com.javimay.rickmortyapp.data.db.entities.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDto(
    val created: String,
    val gender: String,
    val characterId: Long,
    val image: Bitmap,
    val episodeList: List<String>,
    var location: LocationDto?,
    val locationId: Long,
    val name: String,
    var origin: LocationDto?,
    val originId: Long,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable