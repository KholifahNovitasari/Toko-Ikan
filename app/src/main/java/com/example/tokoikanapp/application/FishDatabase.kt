package com.example.tokoikanapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tokoikanapp.dao.FishDao
import com.example.tokoikanapp.model.Fish

@Database(entities = [Fish:: class], version = 2, exportSchema = false)
abstract class FishDatabase: RoomDatabase() {
    abstract fun fishDao(): FishDao

    companion object{
        private var INSTANCE: FishDatabase? = null

        private val migration1To2: Migration = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE fish_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE fish_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
    
        }

        fun getDatabase(context: Context): FishDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FishDatabase::class.java,
                "fish_database"
                )
                    .addMigrations(migration1To2)
                .allowMainThreadQueries()
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}