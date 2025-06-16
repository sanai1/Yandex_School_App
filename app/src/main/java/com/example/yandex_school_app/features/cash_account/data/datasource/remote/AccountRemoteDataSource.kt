package com.example.yandex_school_app.features.cash_account.data.datasource.remote

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain

interface AccountRemoteDataSource {
    suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>>
    suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain>
}