package com.example.yandex_school_app.common.data.datasource.remote

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.common.domain.entity.TransactionDomain

interface TransactionRemoteDataSource {
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ): ResponseTemplate<List<TransactionDomain>>
}