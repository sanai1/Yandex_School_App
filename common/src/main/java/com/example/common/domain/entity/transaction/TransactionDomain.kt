package com.example.common.domain.entity.transaction

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.category.CategoryDomain
import java.time.LocalDateTime

data class TransactionDomain(
    val id: Int,
    val localId: Long,
    val accountDomain: AccountDomain,
    val categoryDomain: CategoryDomain,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?
)