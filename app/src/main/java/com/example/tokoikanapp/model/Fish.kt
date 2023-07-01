package com.example.tokoikanapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "fish-table")
data class Fish (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
            val name: String,
            val address: String,
            val number: String
) : Parcelable