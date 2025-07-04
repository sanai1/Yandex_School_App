package com.example.cash_account.domain.usecase

import com.example.common.domain.entity.AccountDomain
import com.example.cash_account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend fun getAllCashAccount() = accountRepository.getAllCashAccount()
    suspend fun createCashAccount(accountDomain: AccountDomain) =
        accountRepository.createAccount(accountDomain)

    suspend fun updateCashAccount(accountDomain: AccountDomain) =
        accountRepository.updateAccountById(accountDomain)

    suspend fun deleteCashAccount(accountId: Int) = accountRepository.deleteAccountById(accountId)
}