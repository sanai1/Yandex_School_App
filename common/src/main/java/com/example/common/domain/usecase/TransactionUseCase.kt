package com.example.common.domain.usecase

import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        start: String = LocalDate.now().let { localDate ->
            "${localDate.year}-${
                localDate.monthValue.let {
                    if (it in 0..9) "0$it" else it
                }
            }-${
                localDate.dayOfMonth.let {
                    if (it in 0..9) "0$it" else it
                }
            }"
        },
        finish: String = start
    ) = transactionRepository.getTransactionByPeriod(accountId, start, finish)

    suspend fun createTransaction(
        transaction: TransactionPartDomain
    ) = transactionRepository.createTransaction(transaction)

    suspend fun getTransactionById(
        transactionId: Int
    ) = transactionRepository.getTransactionById(transactionId)

    suspend fun updateTransactionById(
        transactionId: Int,
        transaction: TransactionPartDomain
    ) = transactionRepository.updateTransactionById(transactionId, transaction)

    suspend fun deleteTransactionById(
        transactionId: Int
    ) = transactionRepository.deleteTransactionById(transactionId)

}