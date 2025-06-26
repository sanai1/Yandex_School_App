package com.example.yandex_school_app.common.data.network.client

import com.example.yandex_school_app.common.data.network.BaseUrl
import com.example.yandex_school_app.common.data.network.service.TransactionApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TransactionApiClient {
    val transactionApiService: TransactionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl.getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionApiService::class.java)
    }
}