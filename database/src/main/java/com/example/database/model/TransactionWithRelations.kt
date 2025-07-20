package com.example.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithRelations(
    @Embedded
    val transactionModelDB: TransactionModelDB,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryModelDB: CategoryModelDB,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "id"
    )
    val accountModelDB: AccountModelDB
)
