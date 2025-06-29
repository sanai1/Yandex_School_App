package com.example.common.data.network.service

import com.example.common.data.network.BaseUrl
import com.example.common.data.network.model.TransactionNetwork
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApiService {
    @GET("transactions/account/{accountId}/period")
    fun getTransactionsByPeriod(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<List<TransactionNetwork>>
}