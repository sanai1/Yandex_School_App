package com.example.common.data.repository

import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.TransactionDomain
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
}