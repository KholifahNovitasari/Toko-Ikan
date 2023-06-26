package com.example.tokoikanapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tokoikanapp.model.Fish
import kotlinx.coroutines.flow.Flow

@Dao
interface FishDao {
    @Query("SELECT * FROM `fish-table` ORDER BY name ASC")
    fun getAllFish(): Flow<List<Fish>>

    @Insert
    suspend fun insertFish(fish: Fish)

    @Delete
    suspend fun deleteFish(fish: Fish)

    @Update fun updateFish(fish: Fish)
}