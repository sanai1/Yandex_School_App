package com.example.yandex_school_app.features.category.data.network

import com.example.yandex_school_app.common.data.CategoryNetwork
import com.example.yandex_school_app.common.data.network.BaseUrl
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CategoryApiService {
    @GET("categories")
    fun getCategories(
        @Header("Authorization") token: String = BaseUrl.getToken()
    ): Call<List<CategoryNetwork>>
}