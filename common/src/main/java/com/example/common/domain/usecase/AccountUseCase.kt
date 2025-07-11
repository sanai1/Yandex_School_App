package com.example.common.domain.usecase

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.repository.AccountRepository
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