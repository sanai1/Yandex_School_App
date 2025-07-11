package com.example.network.service

import com.example.network.BaseUrl
import com.example.network.model.transaction.request.TransactionRequestNetwork
import com.example.network.model.transaction.response.TransactionResponseNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApiService {
    @GET("transactions/account/{accountId}/period")
    fun getTransactionsByPeriod(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<List<TransactionResponseNetwork>>

    @POST("transactions")
    suspend fun createTransaction(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Body transaction: TransactionRequestNetwork
    ): Response<TransactionRequestNetwork>

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("id") transactionId: Int
    ): Response<TransactionResponseNetwork>

    @PUT("transactions/{id}")
    suspend fun updateTransactionById(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("id") transactionId: Int,
        @Body transaction: TransactionRequestNetwork
    ): Response<TransactionResponseNetwork>

    @DELETE("transactions/{id}")
    suspend fun deleteTransactionById(
        @Header("Authorization") token: String = BaseUrl.getToken(),
        @Path("id") transactionId: Int
    ): Response<ResponseBody>
}