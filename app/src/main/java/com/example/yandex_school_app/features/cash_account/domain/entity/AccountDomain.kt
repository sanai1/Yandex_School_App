package com.example.yandex_school_app.features.cash_account.domain.entity

data class AccountDomain(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)