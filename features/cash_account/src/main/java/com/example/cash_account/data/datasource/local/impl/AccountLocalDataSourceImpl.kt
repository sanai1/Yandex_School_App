package com.example.cash_account.data.datasource.local.impl

import com.example.cash_account.data.datasource.local.AccountLocalDataSource
import com.example.cash_account.data.mapper.AccountMapper
import com.example.common.domain.entity.account.AccountDomain
import com.example.database.dao.AccountDao
import com.example.network.ResponseTemplate
import javax.inject.Inject

class AccountLocalDataSourceImpl @Inject constructor(
    private val accountMapper: AccountMapper,
    private val accountDao: AccountDao
) : AccountLocalDataSource {
    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = accountDao.getAll().map { accountMapper.toAccountDomain(it) }
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<Unit> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = accountDao.insert(accountMapper.toAccountModelDB(accountDomain))
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun updateAccount(accountDomain: AccountDomain): ResponseTemplate<Unit> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = accountDao.update(accountMapper.toAccountModelDB(accountDomain))
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun deleteAccountById(accountId: Long): ResponseTemplate<Unit> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = accountDao.delete(accountId)
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }
}