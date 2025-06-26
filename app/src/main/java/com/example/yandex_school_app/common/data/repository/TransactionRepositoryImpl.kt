package com.example.yandex_school_app.common.data.repository

import com.example.yandex_school_app.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import com.example.yandex_school_app.common.domain.repository.TransactionRepository
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