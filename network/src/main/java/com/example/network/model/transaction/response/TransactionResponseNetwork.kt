package com.example.network.model.transaction.response

import com.example.network.model.category.CategoryNetwork

data class TransactionResponseNetwork(
    val id: Int,
    val account: Account,
    val category: CategoryNetwork,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
) {
    data class Account(
        val id: Int,
        val name: String,
        val balance: String,
        val currency: String
    )
}