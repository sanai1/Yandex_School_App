package com.example.cash_account.data.datasource.remote.impl

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.AccountDomain
import com.example.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.cash_account.data.mapper.AccountMapper
import com.example.network.service.AccountApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val accountMapper: AccountMapper,
    private val accountApiService: AccountApiService
) : AccountRemoteDataSource {
    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        var response = networkAllCashAccount()
        repeat(3) {
            if (response.code() == 500) {
                delay(2000)
                response = networkAllCashAccount()
            } else return@repeat
        }
        return when (response.code()) {
            200, 201, 204 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = response.body()?.map { accountMapper.toAccountDomain(it) }
            )

            401 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.UNAUTHORIZED,
                body = null
            )

            500 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_SERVER,
                body = null
            )

            else -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ALL_BAD,
                body = null
            )
        }
    }

    override suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain> {
        var response = networkCreateAccount(accountDomain)
        repeat(3) {
            if (response.code() == 500) {
                delay(2000)
                response = networkCreateAccount(accountDomain)
            } else return@repeat
        }
        return when (response.code()) {
            201 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = response.body()?.let { accountMapper.toAccountDomain(it) }
            )

            400 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )

            401 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.UNAUTHORIZED,
                body = null
            )

            500 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_SERVER,
                body = null
            )

            else -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ALL_BAD,
                body = null
            )
        }
    }

    private fun networkAllCashAccount() = accountApiService.getAllCashAccount().execute()

    private fun networkCreateAccount(accountDomain: AccountDomain) =
        accountApiService.createAccount(
            account = accountMapper.toAccountRequest(accountDomain)
        ).execute()
}