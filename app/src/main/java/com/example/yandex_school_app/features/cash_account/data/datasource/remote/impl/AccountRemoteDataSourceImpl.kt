package com.example.yandex_school_app.features.cash_account.data.datasource.remote.impl

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.yandex_school_app.features.cash_account.data.mapper.AccountMapper
import com.example.yandex_school_app.features.cash_account.data.network.client.AccountApiClient
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val accountMapper: AccountMapper
) : AccountRemoteDataSource {
    override suspend fun getAllCashAccount(): ResponseTemplate<List<AccountDomain>> {
        val response = AccountApiClient.accountApiService.getAllCashAccount().execute()
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
        val response = AccountApiClient.accountApiService.createAccount(
            account = accountMapper.toAccountRequest(accountDomain)
        ).execute()
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
}