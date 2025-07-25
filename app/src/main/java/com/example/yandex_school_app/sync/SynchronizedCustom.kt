package com.example.yandex_school_app.sync

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.cash_account.data.datasource.local.AccountLocalDataSource
import com.example.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.category.data.datasource.local.CategoryLocalDataSource
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.common.data.datasource.local.TransactionLocalDataSource
import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.common.data.mapper.TransactionMapper
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.store.NamedStore
import com.example.network.ResponseTemplate
import java.time.LocalDateTime
import javax.inject.Inject

class SynchronizedCustom @Inject constructor(
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val transactionRemoteDataSource: TransactionRemoteDataSource,
    private val accountLocalDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val transactionMapper: TransactionMapper,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun sync() {
        if (sharedPreferences.getBoolean(NamedStore.IS_FIRST_RUN, true)) {
            firstSync()
            sharedPreferences.edit { putBoolean(NamedStore.IS_FIRST_RUN, false) }
        } else {
            syncAccount()
        }
    }

    private suspend fun firstSync() {
        categoryRemoteDataSource.getCategories().body?.forEach { categoryDomain ->
            categoryLocalDataSource.creteCategory(categoryDomain)
        }
        accountRemoteDataSource.getAllCashAccount().body?.forEach { accountDomain ->
            accountLocalDataSource.createAccount(accountDomain)
            transactionRemoteDataSource.getTransactionsByPeriod(
                accountId = accountDomain.id,
                startDate = "1900-01-01",
                finishDate = "2150-01-01"
            ).body?.forEach { transactionDomain ->
                transactionLocalDataSource.createTransaction(
                    transactionMapper.toTransactionPathDomain(
                        transactionDomain = transactionDomain,
                        accountId = accountLocalDataSource.getAccountLocalIdByRemoteId(
                            transactionDomain.accountDomain.id
                        ).toInt(),
                        categoryId = categoryLocalDataSource.getCategoryLocalIdByRemoteId(
                            transactionDomain.categoryDomain.id
                        ).toInt()
                    ), transactionDomain.id
                )
            }
        }
    }

    private suspend fun syncAccount() {
        val revisionLocal = sharedPreferences.getLong(NamedStore.ACCOUNT_LOCAL, 0)
        val revisionRemote = sharedPreferences.getLong(NamedStore.ACCOUNT_REMOTE, 0)
        if (revisionRemote == revisionLocal) return
        val accountRemote = accountRemoteDataSource.getAllCashAccount()
        val accountLocal = accountLocalDataSource.getAllCashAccount()
        fun plusRemote() {
            sharedPreferences.edit {
                putLong(
                    NamedStore.ACCOUNT_REMOTE, sharedPreferences.getLong(
                        NamedStore.ACCOUNT_REMOTE, 0
                    ) + 1
                )
            }
        }
        accountLocal.let { responseTemplate ->
            if (responseTemplate.typeResponse == ResponseTemplate.TypeResponse.SUCCESS &&
                accountRemote.typeResponse == ResponseTemplate.TypeResponse.SUCCESS &&
                responseTemplate.body != null &&
                accountRemote.body != null
            ) {
                responseTemplate.body!!.forEach { accountDomain ->
                    // Локально создали аккаунт, на сервере - нет
                    if (accountDomain.id !in accountRemote.body!!.map { it.id }) {
                        accountRemoteDataSource.createAccount(accountDomain).let {
                            if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                                plusRemote()
                            }
                        }
                    }
                }
            }
        }
        syncTransaction {
            accountRemote.body?.forEach { accountDomain ->
                // Локально удалили, на сервере - нет
                if (accountDomain.id !in (accountLocal.body?.map { it.id } ?: emptyList())) {
                    accountRemoteDataSource.deleteAccountById(accountDomain.id).let {
                        if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                            plusRemote()
                        }
                    }
                }
            }
        }
    }

    private suspend fun syncTransaction(callback: suspend () -> Unit) {
        val revisionLocal = sharedPreferences.getLong(NamedStore.TRANSACTION_LOCAL, 0)
        val revisionRemote = sharedPreferences.getLong(NamedStore.TRANSACTION_REMOTE, 0)
        if (revisionRemote == revisionLocal) return
        val transactionRemote = mutableListOf<TransactionDomain>()
        accountLocalDataSource.getAllCashAccount().body?.forEach { it ->
            transactionRemoteDataSource.getTransactionsByPeriod(
                accountId = it.id,
                startDate = "1900-01-01",
                finishDate = "2150-01-01"
            ).let {
                it.body?.forEach { transaction -> transactionRemote.add(transaction) }
            }
        }
        if (transactionRemote.isEmpty()) return
        fun plusRemoteRevision() {
            sharedPreferences.edit {
                putLong(
                    NamedStore.TRANSACTION_REMOTE,
                    sharedPreferences.getLong(NamedStore.TRANSACTION_REMOTE, 0) + 1
                )
            }
        }

        val transactionLocal =
            transactionLocalDataSource.getTransactionByPeriod(LocalDateTime.MIN, LocalDateTime.MAX)
        transactionLocal.let { it ->
            it.body?.forEach { transactionDomain ->
                // Локально создали транзакцию, на сервере - нет
                if (transactionDomain.id !in transactionRemote.map { it.id }) {
                    transactionRemoteDataSource.createTransaction(
                        transactionMapper.toTransactionPathDomain(
                            transactionDomain = transactionDomain,
                            accountId = accountLocalDataSource.getAccountLocalIdByRemoteId(
                                transactionDomain.accountDomain.id
                            ).toInt(),
                            categoryId = categoryLocalDataSource.getCategoryLocalIdByRemoteId(
                                transactionDomain.categoryDomain.id
                            ).toInt()
                        )
                    ).let {
                        if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                            plusRemoteRevision()
                        }
                    }
                } else
                // Локально обновили транзакцию, на сервере - нет
                    if (transactionDomain.updateDate.isAfter(transactionRemote.find { it.id == transactionDomain.id }!!.updateDate)) {
                        transactionRemoteDataSource.updateTransactionById(
                            transactionDomain.id,
                            transactionMapper.toTransactionPathDomain(
                                transactionDomain = transactionDomain,
                                accountId = accountLocalDataSource.getAccountLocalIdByRemoteId(
                                    transactionDomain.accountDomain.id
                                ).toInt(),
                                categoryId = categoryLocalDataSource.getCategoryLocalIdByRemoteId(
                                    transactionDomain.categoryDomain.id
                                ).toInt()
                            )
                        ).let {
                            if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                                plusRemoteRevision()
                            }
                        }
                    }
            }
        }
        transactionRemote.forEach { transactionDomain ->
            // Локально удалили транзакцию, на сервере - нет
            if (transactionDomain.id !in (transactionLocal.body?.map { it.id } ?: emptyList())) {
                transactionRemoteDataSource.deleteTransactionById(transactionDomain.id).let {
                    if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                        plusRemoteRevision()
                    }
                }
            }
        }
        callback.invoke()
    }
}