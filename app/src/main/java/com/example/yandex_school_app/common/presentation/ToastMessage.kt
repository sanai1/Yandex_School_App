package com.example.yandex_school_app.common.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ToastMessage() {
    ToastController.message?.let { message ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
                .pointerInput(Unit) { detectTapGestures {} },
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = message,
                color = Color.White,
                modifier = Modifier
                    .background(
                        Color.Black.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            )
        }
        LaunchedEffect(message) {
            delay(2500)
            ToastController.hideToast()
        }
    }
}