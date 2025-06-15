package com.example.yandex_school_app.features.cash_account.data.network.service

import com.example.yandex_school_app.common.data.network.BaseUrl
import com.example.yandex_school_app.features.cash_account.data.network.model.AccountNetwork
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface AccountApiService {
    @GET("accounts")
    fun getAllCashAccount(
        @Header("Authorization") token: String = BaseUrl.getToken()
    ): Call<List<AccountNetwork>>
}