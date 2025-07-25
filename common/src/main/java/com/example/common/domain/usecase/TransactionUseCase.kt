package com.example.common.domain.usecase

import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.domain.repository.TransactionRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getTransactionsByPeriod(
        start: LocalDate = LocalDate.now(),
        finish: LocalDate = start,
        accountId: Int = 0
    ) = transactionRepository.getTransactionByPeriod(
        LocalDateTime.of(start, LocalTime.MIN),
        LocalDateTime.of(finish, LocalTime.MAX),
        accountId
    )

    suspend fun createTransaction(
        transaction: TransactionPartDomain
    ) = transactionRepository.createTransaction(transaction)

    suspend fun updateTransactionById(
        transactionId: Int,
        transaction: TransactionPartDomain
    ) = transactionRepository.updateTransactionById(transactionId, transaction)

    suspend fun deleteTransactionById(
        transactionId: Int
    ) = transactionRepository.deleteTransactionById(transactionId)

}