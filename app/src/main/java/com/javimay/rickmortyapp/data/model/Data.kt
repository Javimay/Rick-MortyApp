package com.javimay.rickmortyapp.data.model

import com.javimay.rickmortyapp.data.model.DataInfo
data class Data(
    val info: DataInfo,
    val results: List<Result>
) {
    constructor(): this(DataInfo(), emptyList())
}

