package com.example.cash_account.data.datasource.remote.impl

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.AccountDomain
import com.example.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.cash_account.data.mapper.AccountMapper
import com.example.network.check.NoConnectivityException
import com.example.network.service.AccountApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val accountMapper: AccountMapper,
    private val accountApiService: AccountApiService
) : AccountRemoteDataSource {
    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        try {
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
        } catch (e: NoConnectivityException) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NETWORK_PROBLEM,
                body = null
            )
        }
    }

    override suspend fun createAccount(accountDomain: AccountDomain): ResponseTemplate<AccountDomain> {
        try {
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
        } catch (_: NoConnectivityException) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NETWORK_PROBLEM,
                body = null
            )
        }
    }

    override suspend fun updateAccountById(accountDomain: AccountDomain): ResponseTemplate<AccountDomain> {
        try {
            var response = networkUpdateAccount(accountDomain)
            repeat(3) {
                if (response.code() == 500) {
                    delay(2000)
                    response = networkUpdateAccount(accountDomain)
                } else return@repeat
            }
            return when (response.code()) {
                200 -> ResponseTemplate(
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

                404 -> ResponseTemplate(
                    typeResponse = ResponseTemplate.TypeResponse.NOT_FOUND,
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
        } catch (_: NoConnectivityException) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NETWORK_PROBLEM,
                body = null
            )
        }
    }

    private fun networkAllCashAccount() = accountApiService.getAllCashAccount().execute()

    private fun networkCreateAccount(accountDomain: AccountDomain) =
        accountApiService.createAccount(
            account = accountMapper.toAccountRequest(accountDomain)
        ).execute()

    private fun networkUpdateAccount(accountDomain: AccountDomain) =
        accountApiService.updateAccountById(
            accountId = accountDomain.id,
            account = accountMapper.toAccountRequest(accountDomain)
        ).execute()
}