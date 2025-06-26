package com.example.yandex_school_app.features.cash_account.domain.usecase

import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.features.cash_account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend fun getAllCashAccount() = accountRepository.getAllCashAccount()
    suspend fun createCashAccount(accountDomain: AccountDomain) =
        accountRepository.createAccount(accountDomain)
}