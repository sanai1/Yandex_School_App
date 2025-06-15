package com.example.yandex_school_app.features.cash_account.data.network.model

data class AccountNetwork(
    val id: Long,
    val userId: Long,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)