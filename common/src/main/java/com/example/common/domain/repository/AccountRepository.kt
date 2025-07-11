package com.example.common.domain.repository

import com.example.common.domain.entity.account.AccountDomain
import com.example.network.ResponseTemplate

interface AccountRepository {
    suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>>
    suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain>
    suspend fun updateAccountById(accountDomain: AccountDomain): ResponseTemplate<AccountDomain>
    suspend fun deleteAccountById(accountId: Int): ResponseTemplate<Unit>
}