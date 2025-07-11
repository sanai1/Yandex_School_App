package com.example.network.model.transaction.request

data class TransactionRequestNetwork(
    val id: Int?,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String,
    val createdAt: String?,
    val updatedAt: String?
)
