package com.example.common.data.datasource.remote.impl

import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.common.data.mapper.ResponseCodeMapper
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
    private val responseCodeMapper: ResponseCodeMapper,
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
        try {
            var response = networkCreateTransaction(transaction)
            repeat(3) {
                if (response.code() == 500) {
                    delay(2000)
                    response = networkCreateTransaction(transaction)
                } else return@repeat
            }
            return ResponseTemplate(
                typeResponse = responseCodeMapper.mapResponseCode(response.code()),
                body = response.body()?.let { transactionMapper.toTransactionPathDomain(it) }
            )
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

    override suspend fun getTransactionById(transactionId: Int): ResponseTemplate<TransactionDomain> {
        try {
            var response = networkTransactionById(transactionId)
            repeat(3) {
                if (response.code() == 500) {
                    delay(2000)
                    response = networkTransactionById(transactionId)
                } else return@repeat
            }
            return ResponseTemplate(
                typeResponse = responseCodeMapper.mapResponseCode(response.code()),
                body = response.body()?.let { transactionMapper.toTransactionDomain(it) }
            )
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

    override suspend fun updateTransactionById(
        transactionId: Int,
        transaction: TransactionPartDomain
    ): ResponseTemplate<TransactionDomain> {
        try {
            var response = networkUpdateTransaction(transactionId, transaction)
            repeat(3) {
                if (response.code() == 500) {
                    delay(2000)
                    response = networkUpdateTransaction(transactionId, transaction)
                } else return@repeat
            }
            return ResponseTemplate(
                typeResponse = responseCodeMapper.mapResponseCode(response.code()),
                body = response.body()?.let { transactionMapper.toTransactionDomain(it) }
            )
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

    override suspend fun deleteTransactionById(transactionId: Int): ResponseTemplate<Unit> {
        try {
            var response = networkDeleteTransaction(transactionId)
            repeat(3) {
                if (response.code() == 500) {
                    delay(2000)
                    response = networkDeleteTransaction(transactionId)
                } else return@repeat
            }
            return ResponseTemplate(
                typeResponse = responseCodeMapper.mapResponseCode(response.code()),
                body = response.body()?.let { Unit }
            )
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

    private fun networkTransactionsByPeriod(
        accountId: Int,
        startDate: String,
        finishDate: String
    ) = transactionApiService.getTransactionsByPeriod(
        accountId = accountId,
        startDate = startDate,
        endDate = finishDate
    ).execute()

    private suspend fun networkCreateTransaction(
        transaction: TransactionPartDomain
    ) = transactionApiService.createTransaction(
        transaction = transactionMapper.toTransactionRequestNetwork(transaction)
    )

    private suspend fun networkTransactionById(
        transactionId: Int
    ) = transactionApiService.getTransactionById(transactionId = transactionId)

    private suspend fun networkUpdateTransaction(
        transactionId: Int,
        transactionPartDomain: TransactionPartDomain
    ) = transactionApiService.updateTransactionById(
        transactionId = transactionId,
        transaction = transactionMapper.toTransactionRequestNetwork(transactionPartDomain)
    )

    private suspend fun networkDeleteTransaction(
        transactionId: Int
    ) = transactionApiService.deleteTransactionById(
        transactionId = transactionId
    )
}