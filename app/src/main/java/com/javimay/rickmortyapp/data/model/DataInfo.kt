package com.javimay.rickmortyapp.data.model

data class DataInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any?
) {
    constructor() : this(0,"",0,null)

}

