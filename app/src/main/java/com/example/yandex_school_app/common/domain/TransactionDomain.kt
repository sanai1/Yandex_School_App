package com.example.yandex_school_app.common.domain

import com.example.yandex_school_app.category.domain.CategoryDomain

data class TransactionDomain(
    val categoryDomain: CategoryDomain,
    val comment: String?,
    val amount: String
)
