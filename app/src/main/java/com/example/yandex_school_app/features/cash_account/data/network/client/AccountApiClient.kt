package com.example.yandex_school_app.features.cash_account.data.network.client

import com.example.yandex_school_app.common.data.network.BaseUrl
import com.example.yandex_school_app.features.cash_account.data.network.service.AccountApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccountApiClient {
    val accountApiService: AccountApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl.getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccountApiService::class.java)
    }
}