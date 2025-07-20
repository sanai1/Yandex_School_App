package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryModelDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val remoteId: Long,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)
