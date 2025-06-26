package com.example.yandex_school_app.common.data.network

object BaseUrl {
    private var BASE_URL: String? = null
    private var API_TOKEN: String? = null

    init {
        BASE_URL = "https://shmr-finance.ru/api/v1/"
        API_TOKEN = "Bearer $token"
    }

    fun getUrl() = BASE_URL!!

    fun getToken() = API_TOKEN!!
}