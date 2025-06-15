package com.example.yandex_school_app.features.category.data.network

import com.example.yandex_school_app.common.data.network.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CategoryApiClient {
    val categoryApiService: CategoryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl.getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApiService::class.java)
    }
}