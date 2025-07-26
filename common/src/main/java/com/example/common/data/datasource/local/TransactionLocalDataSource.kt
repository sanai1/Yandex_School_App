package com.example.common.data.datasource.local

import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.network.ResponseTemplate
import java.time.LocalDateTime

interface TransactionLocalDataSource {
    suspend fun getTransactionByPeriod(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): ResponseTemplate<List<TransactionDomain>>

    suspend fun getTransactionByPeriodWithAccountId(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        accountId: Int
    ): ResponseTemplate<List<TransactionDomain>>

    suspend fun createTransaction(
        transactionPartDomain: TransactionPartDomain,
        remoteId: Int
    ): ResponseTemplate<Unit>

    suspend fun updateTransaction(
        transactionPartDomain: TransactionPartDomain,
        remoteId: Int
    ): ResponseTemplate<Unit>

    suspend fun deleteTransactionById(transactionId: Int): ResponseTemplate<Unit>
}