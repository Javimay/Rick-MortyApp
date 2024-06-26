package com.javimay.rickmortyapp.data.model

data class Data(
    val info: DataInfo,
    val results: List<ResultDto>
) {
    constructor(): this(DataInfo(), emptyList())
}

