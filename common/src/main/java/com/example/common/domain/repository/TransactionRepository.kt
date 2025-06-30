package com.example.common.domain.repository

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.TransactionDomain

interface TransactionRepository {
    suspend fun getTransactionByPeriod(
        accountId: Int,
        start: String,
        finish: String
    ): ResponseTemplate<List<TransactionDomain>>
}