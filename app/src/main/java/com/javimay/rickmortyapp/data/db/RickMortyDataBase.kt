package com.javimay.rickmortyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javimay.rickmortyapp.data.db.daos.ICharacterDao
import com.javimay.rickmortyapp.data.db.daos.IEpisodeDao
import com.javimay.rickmortyapp.data.db.daos.ILocationDao
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.Episode
import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.data.model.relations.CharacterEpisodeCrossRef
import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.data.model.typesconverter.ImageTypeConverter
import com.javimay.rickmortyapp.data.model.typesconverter.StringListTypeConverter

@Database(
    entities = [
        Character::class,
        Episode::class,
        Location::class,
        CharacterEpisodeCrossRef::class,
        CharacterLocationCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImageTypeConverter::class,
    StringListTypeConverter::class
)
abstract class RickMortyDataBase : RoomDatabase() {
    abstract fun characterDao(): ICharacterDao
    abstract fun episodeDao(): IEpisodeDao
    abstract fun locationDao(): ILocationDao
}