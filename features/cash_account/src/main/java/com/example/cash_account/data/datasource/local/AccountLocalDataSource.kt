package com.example.cash_account.data.datasource.local

import com.example.common.domain.entity.account.AccountDomain
import com.example.network.ResponseTemplate

interface AccountLocalDataSource {
    suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>>
    suspend fun getAccountLocalIdByRemoteId(remoteId: Int): Long
    suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<Unit>
    suspend fun updateAccount(accountDomain: AccountDomain): ResponseTemplate<Unit>
    suspend fun deleteAccountById(accountId: Long): ResponseTemplate<Unit>
}