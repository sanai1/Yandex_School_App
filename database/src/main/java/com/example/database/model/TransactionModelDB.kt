package com.example.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryModelDB::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AccountModelDB::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId"), Index("accountId")]
)
data class TransactionModelDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val remoteId: Int,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val categoryId: Long,
    val accountId: Long
)
