package com.example.pie

import androidx.compose.ui.graphics.Color

data class PieChartData(
    val value: Float,
    val color: Color,
    val name: String,
    val description: String = ""
)
