package com.example.network.service

import com.example.network.BaseUrl
import com.example.network.model.cash_account.request.AccountRequestNetwork
import com.example.network.model.cash_account.response.AccountResponseNetwork
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @PUT("accounts/{id}")
    fun updateAccountById(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("id") accountId: Int,
        @Body account: AccountRequestNetwork
    ): Call<AccountResponseNetwork>
}