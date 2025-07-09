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
                ResponseTemplate.TypeResponse.SUCCESS -> Text(ResponseTemplate.TypeResponse.SUCCESS.text)
                ResponseTemplate.TypeResponse.UNAUTHORIZED -> Text(ResponseTemplate.TypeResponse.UNAUTHORIZED.text)
                ResponseTemplate.TypeResponse.NOT_FOUND -> Text(ResponseTemplate.TypeResponse.NOT_FOUND.text)
                ResponseTemplate.TypeResponse.ERROR_SERVER -> Text(ResponseTemplate.TypeResponse.ERROR_SERVER.text)
                ResponseTemplate.TypeResponse.ERROR_CLIENT -> Text(ResponseTemplate.TypeResponse.ERROR_CLIENT.text)
                ResponseTemplate.TypeResponse.MANY_REQUEST -> Text(ResponseTemplate.TypeResponse.MANY_REQUEST.text)
                ResponseTemplate.TypeResponse.NETWORK_PROBLEM -> Text(ResponseTemplate.TypeResponse.NETWORK_PROBLEM.text)
                ResponseTemplate.TypeResponse.ALL_BAD -> Text(ResponseTemplate.TypeResponse.ALL_BAD.text)
            }
        }
    }
}