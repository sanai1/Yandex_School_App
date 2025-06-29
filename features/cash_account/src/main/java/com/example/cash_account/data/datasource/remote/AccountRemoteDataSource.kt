package com.example.cash_account.data.datasource.remote

import com.example.common.data.network.ResponseTemplate
import com.example.common.domain.entity.AccountDomain

interface AccountRemoteDataSource {
    suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>>
    suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain>
}