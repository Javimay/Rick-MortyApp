package com.javimay.rickmortyapp.data.model

data class EpisodeData(
    val info: DataInfo,
    val results: List<EpisodeResult>
) {
    constructor(): this(DataInfo(), emptyList())
}

