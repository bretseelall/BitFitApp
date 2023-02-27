package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {
    @Query("SELECT * FROM health_table")
    fun getAll(): Flow<List<HealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(health: HealthEntity)

    @Query("DELETE FROM health_table")
    fun deleteAll()
}