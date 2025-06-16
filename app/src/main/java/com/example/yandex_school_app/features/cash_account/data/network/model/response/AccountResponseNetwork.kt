package com.example.yandex_school_app.features.cash_account.data.network.model.response

data class AccountResponseNetwork(
    val id: Long,
    val userId: Long,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)