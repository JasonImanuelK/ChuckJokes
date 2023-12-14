package com.hans.chuckjokes.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hans.chuckjokes.core.data.source.local.entity.JokesEntity

@Database(entities = [JokesEntity::class], version = 1, exportSchema = false)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun jokesDao(): JokesDao

    companion object {
        @Volatile
        private var INSTANCE: JokesDatabase? = null

        fun getInstance(context: Context): JokesDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JokesDatabase::class.java,
                    "Jokes.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
    }
}