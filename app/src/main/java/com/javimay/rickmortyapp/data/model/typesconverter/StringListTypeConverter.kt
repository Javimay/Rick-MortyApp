package com.javimay.rickmortyapp.data.model.typesconverter

import androidx.room.TypeConverter

class StringListTypeConverter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}