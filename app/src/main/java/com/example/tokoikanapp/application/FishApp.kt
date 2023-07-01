package com.example.tokoikanapp.application

import android.app.Application
import com.example.tokoikanapp.repository.FishRepository

class FishApp: Application() {
    val database by lazy { FishDatabase.getDatabase(this) }
    val repository by lazy { FishRepository(database.fishDao()) }
}