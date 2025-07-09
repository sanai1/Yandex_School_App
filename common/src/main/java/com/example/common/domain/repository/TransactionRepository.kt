package com.example.common.domain.repository

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain

interface TransactionRepository {
    suspend fun getTransactionByPeriod(
        accountId: Int,
        start: String,
        finish: String
    ): ResponseTemplate<List<TransactionDomain>>

    suspend fun createTransaction(
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionPartDomain>

    suspend fun getTransactionById(
        transactionId: Int
    ): ResponseTemplate<TransactionDomain>

    suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionPartDomain>

    suspend fun deleteTransactionById(
        transactionId: Int
    ): ResponseTemplate<Unit>
}