package com.example.network.model.transaction.request

data class TransactionRequestNetwork(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String
)
