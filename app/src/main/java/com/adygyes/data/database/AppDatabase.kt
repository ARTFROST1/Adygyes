package com.adygyes.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.adygyes.data.database.dao.AttractionDao
import com.adygyes.data.database.entities.AttractionEntity

@Database(
    entities = [AttractionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun attractionDao(): AttractionDao
    
    companion object {
        const val DATABASE_NAME = "adygyes_database"
    }
}
