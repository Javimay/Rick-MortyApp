package com.javimay.rickmortyapp.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDto(
    val created: String,
    val gender: String,
    val characterId: Long,
    val image: Bitmap,
    val episodeList: List<String>,
    val locationId: Long,
    val name: String,
    val originId: Long,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable