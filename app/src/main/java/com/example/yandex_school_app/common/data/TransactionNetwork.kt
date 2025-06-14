package com.example.yandex_school_app.common.data

data class TransactionNetwork(
    val id: Long,
    val account: String,
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
