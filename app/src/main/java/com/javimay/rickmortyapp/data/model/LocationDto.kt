package com.javimay.rickmortyapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationDto(
    val created: String,
    val dimension: String?,
    val locationId: Long,
    val name: String?,
    val type: String?,
    val url: String
) : Parcelable
