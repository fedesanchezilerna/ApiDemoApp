package org.ilerna.apidemoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.ilerna.apidemoapp.data.local.dao.CharacterDao
import org.ilerna.apidemoapp.data.local.entity.FavoriteCharacterEntity

/**
 * CharacterDatabase - Room database for storing favorite characters
 */
@Database(entities = [FavoriteCharacterEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
