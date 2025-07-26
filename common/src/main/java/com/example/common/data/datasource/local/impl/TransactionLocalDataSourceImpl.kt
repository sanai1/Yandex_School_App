package com.example.common.data.datasource.local.impl

import com.example.common.data.datasource.local.TransactionLocalDataSource
import com.example.common.data.mapper.TransactionMapper
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.database.dao.TransactionDao
import com.example.network.ResponseTemplate
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionLocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val transactionMapper: TransactionMapper
) : TransactionLocalDataSource {
    override suspend fun getTransactionByPeriod(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): ResponseTemplate<List<TransactionDomain>> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = transactionDao.getAllByPeriod(startDate, endDate)
                    .map { transactionMapper.toTransactionDomain(it) }
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun getTransactionByPeriodWithAccountId(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        accountId: Int
    ): ResponseTemplate<List<TransactionDomain>> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = transactionDao.getAllByPeriodWithAccountId(startDate, endDate, accountId)
                    .map {
                        transactionMapper.toTransactionDomain(it)
                    }
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun createTransaction(
        transactionPartDomain: TransactionPartDomain,
        remoteId: Int
    ): ResponseTemplate<Unit> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = transactionDao.insert(
                    transactionMapper.toTransactionModelDB(
                        transactionPartDomain,
                        remoteId
                    )
                )
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun updateTransaction(
        transactionPartDomain: TransactionPartDomain,
        remoteId: Int
    ): ResponseTemplate<Unit> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = transactionDao.update(
                    transactionMapper.toTransactionModelDB(
                        transactionPartDomain,
                        remoteId
                    )
                )
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun deleteTransactionById(transactionId: Int): ResponseTemplate<Unit> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = transactionDao.delete(transactionId.toLong())
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }
}