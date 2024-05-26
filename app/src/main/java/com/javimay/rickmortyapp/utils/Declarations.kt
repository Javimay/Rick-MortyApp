package com.javimay.rickmortyapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.util.regex.Pattern

fun getIdsFromStringList(stringList: List<String>): List<Int> =
    stringList.map {
        Pattern.compile("[0-9]+").matcher(it).group().toInt()
    }

suspend fun getBitmap(context: Context, url: String): Bitmap {
    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(url)
        .build()
    val result = (loading.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}
