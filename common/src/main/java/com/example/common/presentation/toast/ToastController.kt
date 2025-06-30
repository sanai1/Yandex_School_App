package com.example.common.presentation.toast

import androidx.compose.runtime.mutableStateOf

object ToastController {
    private val _message = mutableStateOf<String?>(null)
    val message: String? get() = _message.value

    fun showToast(message: String) {
        _message.value = message
    }

    fun hideToast() {
        _message.value = null
    }
}