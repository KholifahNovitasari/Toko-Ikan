package com.example.tokoikanapp.repository

import com.example.tokoikanapp.dao.TireDao
import com.example.tokoikanapp.model.Tire
import kotlinx.coroutines.flow.Flow

class TireRepository(private val tireDao: TireDao) {
    val allTire: Flow<List<Tire>> = tireDao.getAllTire()
    suspend fun insertTire(tire: Tire) {
        tireDao.insertTire(tire)
    }
    suspend fun deleteTire(tire: Tire) {
        tireDao.deleteTire(tire)
    }
    suspend fun updateTire(tire: Tire) {
        tireDao.updateTire(tire)
    }
}