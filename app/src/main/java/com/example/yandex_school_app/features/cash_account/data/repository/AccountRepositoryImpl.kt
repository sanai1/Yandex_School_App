package com.example.yandex_school_app.features.cash_account.data.repository

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.features.cash_account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {
    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        return accountRemoteDataSource.getAllCashAccount()
    }
}