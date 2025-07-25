package com.example.common.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.common.data.datasource.local.TransactionLocalDataSource
import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.domain.repository.TransactionRepository
import com.example.common.store.NamedStore
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionRemoteDataSource: TransactionRemoteDataSource,
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val sharedPreferences: SharedPreferences
) : TransactionRepository {
    override suspend fun getTransactionByPeriod(
        start: LocalDateTime,
        finish: LocalDateTime,
        accountId: Int
    ): ResponseTemplate<List<TransactionDomain>> {
        return if (accountId == 0) {
            transactionLocalDataSource.getTransactionByPeriod(start, finish)
        } else {
            transactionLocalDataSource.getTransactionByPeriodWithAccountId(
                start,
                finish,
                accountId
            )
        }
    }

    override suspend fun createTransaction(transaction: TransactionPartDomain): ResponseTemplate<Unit> {
        var remoteId = 0
        try {
            transactionRemoteDataSource.createTransaction(transaction).let {
                it.body?.id?.let { id -> remoteId = id }
                plusRemote(it.typeResponse)
            }
        } finally {
        }
        return transactionLocalDataSource.createTransaction(transaction, remoteId).let {
            ResponseTemplate(
                typeResponse = it.typeResponse,
                body = Unit
            ).apply { if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) plusLocal() }
        }
    }

    override suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<Unit> {
        try {
            plusRemote(
                transactionRemoteDataSource.updateTransactionById(
                    id,
                    transaction
                ).typeResponse
            )
        } finally {
        }
        return transactionLocalDataSource.updateTransaction(transaction, id).let {
            ResponseTemplate(
                typeResponse = it.typeResponse,
                body = Unit
            ).apply { if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) plusLocal() }
        }
    }

    override suspend fun deleteTransactionById(transactionId: Int): ResponseTemplate<Unit> {
        try {
            plusRemote(transactionRemoteDataSource.deleteTransactionById(transactionId).typeResponse)
        } finally {
        }
        return transactionLocalDataSource.deleteTransactionById(transactionId).let {
            ResponseTemplate(
                typeResponse = it.typeResponse,
                body = Unit
            ).apply { if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) plusLocal() }
        }
    }

    private fun plusRemote(type: ResponseTemplate.TypeResponse) {
        if (type == ResponseTemplate.TypeResponse.SUCCESS) {
            sharedPreferences.edit {
                putLong(
                    NamedStore.TRANSACTION_REMOTE,
                    sharedPreferences.getLong(NamedStore.TRANSACTION_REMOTE, 0) + 1
                )
            }
        }
    }

    private fun plusLocal() {
        sharedPreferences.edit {
            putLong(
                NamedStore.TRANSACTION_LOCAL,
                sharedPreferences.getLong(NamedStore.TRANSACTION_LOCAL, 0) + 1
            )
        }
    }
}