package com.example.common.domain.repository

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import java.time.LocalDateTime

interface TransactionRepository {
    suspend fun getTransactionByPeriod(
        start: LocalDateTime,
        finish: LocalDateTime
    ): ResponseTemplate<List<TransactionDomain>>

    suspend fun createTransaction(
        transaction: TransactionPartDomain
    ): ResponseTemplate<Unit>

    suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<Unit>

    suspend fun deleteTransactionById(
        transactionId: Int
    ): ResponseTemplate<Unit>
}