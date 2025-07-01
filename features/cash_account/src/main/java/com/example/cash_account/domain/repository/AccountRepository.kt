package com.example.cash_account.domain.repository

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.AccountDomain

interface AccountRepository {
    suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>>
    suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain>
    suspend fun updateAccountById(accountDomain: AccountDomain): ResponseTemplate<AccountDomain>
}