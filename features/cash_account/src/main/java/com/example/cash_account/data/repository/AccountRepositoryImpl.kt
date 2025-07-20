package com.example.cash_account.data.repository

import android.content.SharedPreferences
import com.example.cash_account.data.datasource.local.AccountLocalDataSource
import com.example.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.repository.AccountRepository
import com.example.common.store.NamedStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.content.edit

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val accountLocalDateSource: AccountLocalDataSource,
    private val sharedPreferences: SharedPreferences
) : AccountRepository {

    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        return accountLocalDateSource.getAllCashAccount()
    }

    override suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain> {
        // TODO: при создании новой записи remoteId устанавливается 0 независимо от результата запросов в сеть
        CoroutineScope(Dispatchers.IO).launch {
            try {
                plusRemote(accountRemoteDataSource.createAccount(accountDomain).typeResponse)
            } finally {
            }
        }
        return try {
            accountLocalDateSource.createAccount(accountDomain).let {
                ResponseTemplate(
                    typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                    body = accountDomain
                ).apply { plusLocal() }
            }
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun updateAccountById(accountDomain: AccountDomain): ResponseTemplate<Unit> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                plusRemote(accountRemoteDataSource.updateAccountById(accountDomain).typeResponse)
            } finally {
            }
        }
        return try {
            accountLocalDateSource.updateAccount(accountDomain).let {
                ResponseTemplate(
                    typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                    body = Unit
                ).apply { plusLocal() }
            }
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun deleteAccountById(accountId: Int): ResponseTemplate<Unit> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                plusRemote(accountRemoteDataSource.deleteAccountById(accountId).typeResponse)
            } finally {
            }
        }
        return try {
            accountLocalDateSource.deleteAccountById(accountId.toLong()).let {
                ResponseTemplate(
                    typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                    body = Unit
                ).apply { plusLocal() }
            }
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    private fun plusRemote(type: ResponseTemplate.TypeResponse) {
        if (type == ResponseTemplate.TypeResponse.SUCCESS) {
            sharedPreferences.edit {
                putLong(
                    NamedStore.ACCOUNT_REMOTE, sharedPreferences.getLong(
                        NamedStore.ACCOUNT_REMOTE, 0
                    ) + 1
                )
            }
        }
    }

    private fun plusLocal() {
        sharedPreferences.edit {
            putLong(
                NamedStore.ACCOUNT_LOCAL, sharedPreferences.getLong(NamedStore.ACCOUNT_LOCAL, 0) + 1
            )
        }
    }
}