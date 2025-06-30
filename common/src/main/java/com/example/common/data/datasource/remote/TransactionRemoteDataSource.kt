package com.example.common.data.datasource.remote

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.TransactionDomain

interface TransactionRemoteDataSource {
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ): ResponseTemplate<List<TransactionDomain>>
}