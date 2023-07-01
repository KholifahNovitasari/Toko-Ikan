package com.example.tokoikanapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tokoikanapp.dao.FishDao
import com.example.tokoikanapp.model.Fish

@Database(entities = [Fish:: class], version = 1, exportSchema = false)
abstract class FishDatabase: RoomDatabase() {
    abstract fun fishDao(): FishDao

    companion object{
        private var INSTANCE: FishDatabase? = null

        fun getDatabase(context: Context): FishDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FishDatabase::class.java,
                "fish_database"
                )
                .allowMainThreadQueries()
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}