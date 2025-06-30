package com.example.network.model.cash_account.request

data class AccountRequestNetwork(
    val name: String,
    val balance: String,
    val currency: String
)