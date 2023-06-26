package com.example.tokoikanapp.repository

import com.example.tokoikanapp.dao.FishDao
import com.example.tokoikanapp.model.Fish
import kotlinx.coroutines.flow.Flow

class FishRepository(private val fishDao: FishDao) {
    val allFish: Flow<List<Fish>> = fishDao.getAllFish()
    suspend fun insertFish(fish: Fish) {
        fishDao.insertFish(fish)
    }
    suspend fun deleteFish(fish: Fish) {
        fishDao.deleteFish(fish)
    }
    suspend fun updateFish(fish: Fish) {
        fishDao.updateFish(fish)
    }
}