package com.example.common.data.repository

import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionRemoteDataSource: TransactionRemoteDataSource
) : TransactionRepository {
    override suspend fun getTransactionByPeriod(
        accountId: Int,
        start: String,
        finish: String
    ): ResponseTemplate<List<TransactionDomain>> {
        return transactionRemoteDataSource.getTransactionsByPeriod(accountId, start, finish)
    }

    override suspend fun createTransaction(transaction: TransactionPartDomain): ResponseTemplate<TransactionPartDomain> {
        return transactionRemoteDataSource.createTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): ResponseTemplate<TransactionDomain> {
        return transactionRemoteDataSource.getTransactionById(transactionId)
    }

    override suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionDomain> {
        return transactionRemoteDataSource.updateTransactionById(id, transaction)
    }

    override suspend fun deleteTransactionById(transactionId: Int): ResponseTemplate<Unit> {
        return transactionRemoteDataSource.deleteTransactionById(transactionId)
    }
}