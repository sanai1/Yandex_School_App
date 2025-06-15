package com.example.yandex_school_app.common.data.datasource.remote.impl

import com.example.yandex_school_app.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.yandex_school_app.common.data.mapper.TransactionMapper
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.common.data.network.client.TransactionApiClient
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import javax.inject.Inject

class TransactionRemoteDataSourceImpl @Inject constructor(
    private val transactionMapper: TransactionMapper
) : TransactionRemoteDataSource {
    override suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ): ResponseTemplate<List<TransactionDomain>> {
        val response = TransactionApiClient.transactionApiService.getTransactionsByPeriod(
            accountId = accountId,
            startDate = startDate,
            endDate = finishDate
        ).execute()
        return when (response.code()) {
            200, 201, 204 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = response.body()?.map { transactionMapper.toTransactionDomain(it) }
            )

            400 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )

            401 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.UNAUTHORIZED,
                body = null
            )

            404 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NOT_FOUND,
                body = null
            )

            500 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_SERVER,
                body = null
            )

            else -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ALL_BAD,
                body = null
            )
        }
    }
}