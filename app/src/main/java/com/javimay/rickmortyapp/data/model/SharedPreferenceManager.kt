package com.javimay.rickmortyapp.data.model

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.javimay.rickmortyapp.utils.CHARACTERS_PAGE_VALUE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor() {

    var charactersPageValue: Int
        get() = preferences.getInt(CHARACTERS_PAGE_VALUE, 2)
        set(pageValue) { preferences.edit().putInt(CHARACTERS_PAGE_VALUE, pageValue).apply() }

    companion object {
        @Volatile
        private var instance: SharedPreferenceManager? = null

        private lateinit var preferences: SharedPreferences

        fun getInstance(@ApplicationContext context: Context): SharedPreferenceManager =
            instance ?: synchronized(this) {
                instance ?: SharedPreferenceManager().also {
                    instance = it
                    preferences = PreferenceManager.getDefaultSharedPreferences(context)
                }
            }
    }

    /*fun getCharactersPageValue() = charactersPageValue

    fun setCharactersPageValue(pageValue: Int) {
        charactersPageValue = pageValue
    }*/


}