package com.example.cash_account.data.repository

import com.example.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.AccountDomain
import com.example.cash_account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {
    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        return accountRemoteDataSource.getAllCashAccount()
    }

    override suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain> {
        return accountRemoteDataSource.createAccount(accountDomain)
    }

    override suspend fun updateAccountById(accountDomain: AccountDomain): ResponseTemplate<AccountDomain> {
        return accountRemoteDataSource.updateAccountById(accountDomain)
    }

    override suspend fun deleteAccountById(accountId: Int): ResponseTemplate<Unit> {
        return accountRemoteDataSource.deleteAccountById(accountId)
    }
}