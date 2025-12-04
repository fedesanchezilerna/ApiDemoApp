package org.ilerna.apidemoapp

import android.app.Application
import androidx.room.Room
import org.ilerna.apidemoapp.data.local.database.CharacterDatabase

/**
 * CharacterApplication - Application class for managing database singleton
 */
class CharacterApplication : Application() {
    
    companion object {
        lateinit var database: CharacterDatabase
    }
    
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            CharacterDatabase::class.java,
            "character_database"
        ).build()
    }
}
