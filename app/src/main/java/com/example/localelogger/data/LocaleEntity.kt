package com.example.localelogger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locales")
data class LocaleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val iconType: String,
    val title: String,
    val description: String,
    val location: String,
    val timestamp: String
)