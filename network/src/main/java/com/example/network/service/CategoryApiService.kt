package com.example.network.service

import com.example.network.model.category.CategoryNetwork
import com.example.network.BaseUrl
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryApiService {
    @GET("categories")
    fun getCategories(
        @Header("Authorization") token: String = BaseUrl.getToken()
    ): Call<List<CategoryNetwork>>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("isIncome") isIncome: Boolean
    ): Response<List<CategoryNetwork>>
}