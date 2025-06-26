package com.example.yandex_school_app.features.cash_account.data.network.service

import com.example.yandex_school_app.common.data.network.BaseUrl
import com.example.yandex_school_app.features.cash_account.data.network.model.request.AccountRequestNetwork
import com.example.yandex_school_app.features.cash_account.data.network.model.response.AccountResponseNetwork
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AccountApiService {
    @GET("accounts")
    fun getAllCashAccount(
        @Header("Authorization") token: String = BaseUrl.getToken()
    ): Call<List<AccountResponseNetwork>>

    @POST("accounts")
    fun createAccount(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Body account: AccountRequestNetwork
    ): Call<AccountResponseNetwork>
}