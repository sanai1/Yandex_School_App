package com.example.common.data.datasource.remote.impl

import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.common.data.mapper.TransactionMapper
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.network.check.NoConnectivityException
import com.example.network.service.TransactionApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class TransactionRemoteDataSourceImpl @Inject constructor(
    private val transactionMapper: TransactionMapper,
    private val transactionApiService: TransactionApiService
) : TransactionRemoteDataSource {
    override suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ): ResponseTemplate<List<TransactionDomain>> {
        try {
            var response = networkTransactionsByPeriod(accountId, startDate, finishDate)
            repeat(3) {
                if (response.code() == 500) {
                    delay(2000)
                    response = networkTransactionsByPeriod(accountId, startDate, finishDate)
                } else return@repeat
            }
            return when (response.code()) {
                200 -> ResponseTemplate(
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
        } catch (_: NoConnectivityException) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NETWORK_PROBLEM,
                body = null
            )
        } catch (_: Exception) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_SERVER,
                body = null
            )
        }
    }

    override suspend fun createTransaction(transaction: TransactionPartDomain): ResponseTemplate<TransactionPartDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionById(transactionId: Int): ResponseTemplate<TransactionDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransactionById(
        transactionId: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionPartDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransactionById(transactionId: Int): ResponseTemplate<Unit> {
        TODO("Not yet implemented")
    }

    private fun networkTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ) = transactionApiService.getTransactionsByPeriod(
        accountId = accountId,
        startDate = startDate,
        endDate = finishDate
    ).execute()
}