package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountModelDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val remoteId: Long,
    val name: String,
    val balance: String,
    val currency: String
)
