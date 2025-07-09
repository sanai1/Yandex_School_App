package com.example.common.data.datasource.remote

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain

interface TransactionRemoteDataSource {
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ): ResponseTemplate<List<TransactionDomain>>

    suspend fun createTransaction(
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionPartDomain>

    suspend fun getTransactionById(
        transactionId: Int
    ): ResponseTemplate<TransactionDomain>

    suspend fun updateTransactionById(
        transactionId: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionPartDomain>

    suspend fun deleteTransactionById(
        transactionId: Int
    ): ResponseTemplate<Unit>
}