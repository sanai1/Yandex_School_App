package com.example.yandex_school_app.common.domain.usecase

import com.example.yandex_school_app.common.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        start: String = LocalDate.now().let {
            "${it.year}-${
                it.monthValue.let {
                    if (it in 0..9) "0$it" else it
                }
            }-${it.dayOfMonth}"
        },
        finish: String = start
    ) = transactionRepository.getTransactionByPeriod(accountId, start, finish)
}