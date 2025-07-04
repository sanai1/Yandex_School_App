package com.example.common.domain.entity

data class AccountDomain(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: Currency
)