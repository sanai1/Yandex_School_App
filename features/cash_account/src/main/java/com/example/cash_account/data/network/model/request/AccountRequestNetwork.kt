package com.example.cash_account.data.network.model.request

data class AccountRequestNetwork(
    val name: String,
    val balance: String,
    val currency: String
)