package com.example.localelogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocaleEntity::class], version = 1)
abstract class LocaleDatabase : RoomDatabase() {
    abstract fun localeDao(): LocaleDao

    companion object {
        @Volatile
        private var INSTANCE: LocaleDatabase? = null

        fun getDatabase(context: Context): LocaleDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    LocaleDatabase::class.java,
                    "locale_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}