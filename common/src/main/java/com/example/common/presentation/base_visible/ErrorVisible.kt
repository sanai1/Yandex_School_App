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
        Text(message ?: type.text)
    }
}