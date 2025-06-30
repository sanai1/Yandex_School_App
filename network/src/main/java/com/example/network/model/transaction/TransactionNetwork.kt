package com.example.network.model.transaction

import com.example.network.model.category.CategoryNetwork

data class TransactionNetwork(
    val id: Long,
    val account: Account,
    val category: CategoryNetwork,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
) {
    data class Account(
        val id: Long,
        val name: String,
        val balance: String,
        val currency: String
    )
}
