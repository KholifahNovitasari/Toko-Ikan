package com.example.tokoikanapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tokoikanapp.model.Tire
import kotlinx.coroutines.flow.Flow

@Dao
interface TireDao {
    @Query("SELECT * FROM `tire-table` ORDER BY name ASC")
    fun getAllTire(): Flow<List<Tire>>

    @Insert
    suspend fun insertTire(tire: Tire)

    @Delete
    suspend fun deleteTire(tire: Tire)

    @Update fun updateTire(tire: Tire)
}