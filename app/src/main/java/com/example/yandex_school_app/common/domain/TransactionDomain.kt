package com.example.yandex_school_app.common.domain

import com.example.yandex_school_app.features.category.domain.entity.CategoryDomain

data class TransactionDomain(
    val categoryDomain: CategoryDomain,
    val comment: String?,
    val amount: String
)
