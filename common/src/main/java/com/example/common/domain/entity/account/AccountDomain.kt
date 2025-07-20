package com.example.common.domain.entity.account

data class AccountDomain(
    val id: Int,
    val localId: Long,
    val name: String,
    val balance: String,
    val currency: Currency
)