package com.example.yandex_school_app.features.cash_account.domain.repository

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain

interface AccountRepository {
    suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>>
}