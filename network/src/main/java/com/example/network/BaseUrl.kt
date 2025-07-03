package com.example.network

object BaseUrl {
    private const val BASE_URL: String = "https://shmr-finance.ru/api/v1/"
    private const val API_TOKEN: String = "Bearer $token"

    fun getUrl() = BASE_URL

    fun getToken() = API_TOKEN
}