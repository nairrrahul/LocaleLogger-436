package com.example.localelogger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locale: LocaleEntity)

    @Update
    suspend fun update(locale: LocaleEntity)

    @Query("SELECT * FROM locales WHERE id = :id")
    suspend fun getLocaleById(id: Int): LocaleEntity?

    @Query("""
        SELECT * FROM locales ORDER BY 
        strftime('%Y', timestamp) DESC, 
        strftime('%m', timestamp) DESC
    """)
    fun getAllGroupedByMonth(): Flow<List<LocaleEntity>>

    @Query("SELECT iconType, COUNT(*) as count FROM locales GROUP BY iconType ORDER BY count DESC")
    fun getNetCount(): Flow<List<NetCount>>

    @Query("SELECT SUBSTR(timestamp, 1, 2) || '/' || SUBSTR(timestamp, 7, 4) AS monthYear, COUNT(*) AS count FROM locales GROUP BY monthYear ORDER BY count DESC")
    fun getMonthCount(): Flow<List<MonthCount>>
}

data class NetCount(val iconType: String, val count: Int)
data class MonthCount(val monthYear: String, val count: Int)