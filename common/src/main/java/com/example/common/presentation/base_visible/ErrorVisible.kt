package com.example.common.presentation.base_visible

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.network.ResponseTemplate

@Composable
fun ErrorVisible(
    type: ResponseTemplate.TypeResponse,
    message: String? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (message != null) {
            Text(message)
        } else {
            when (type) {
                ResponseTemplate.TypeResponse.SUCCESS -> Text("Данные есть, но что-то пошло не так")
                ResponseTemplate.TypeResponse.UNAUTHORIZED -> Text("Ошибка авторизации")
                ResponseTemplate.TypeResponse.NOT_FOUND -> Text("Ошибка в данных")
                ResponseTemplate.TypeResponse.ERROR_SERVER -> Text("Внутренняя ошибка сервера")
                ResponseTemplate.TypeResponse.ERROR_CLIENT -> Text("Внутренняя ошибка приложения")
                ResponseTemplate.TypeResponse.NETWORK_PROBLEM -> Text("Проблемы с сетью.\nПроверьте подключение к интернету")
                ResponseTemplate.TypeResponse.ALL_BAD -> Text("Неизвесная ошибка")
            }
        }
    }
}