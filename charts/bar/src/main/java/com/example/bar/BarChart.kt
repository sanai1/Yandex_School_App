package com.example.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun BarChart(
    data: List<BarChartData>,
    modifier: Modifier = Modifier,
    cornerRadius: Float = 8f
) {
    if (data.size < 30 || data.map { it.value }.toSet() == setOf(1f)) {
        Text("Недостаточно данных", color = MaterialTheme.colorScheme.onBackground)
        return
    }

    val maxValue = maxOf(data.maxOfOrNull { it.value } ?: 1f, 1f)

    Column(
        modifier = modifier
            .height(300.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = modifier
                .height(225.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(Color.Transparent)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val barWidth = canvasWidth / data.size
            val padding = barWidth * 0.2f

            data.forEachIndexed { index, item ->
                val barHeight = (item.value / maxValue) * canvasHeight

                drawRoundRect(
                    color = if (item.isIncome) Color(0xFF2AE881) else Color(0xFFFF5F00),
                    topLeft = Offset(
                        x = index * barWidth + padding,
                        y = canvasHeight - barHeight
                    ),
                    size = Size(
                        width = barWidth - 2 * padding,
                        height = barHeight
                    ),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }
        Row(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            val nowDate = LocalDate.now()
            Text(
                nowDate.minusDays(data.size.toLong())
                    .let { it -> "${it.dayOfMonth.let { if (it in 0..9) "0$it" else it }}.${it.monthValue.let { if (it in 0..9) "0$it" else it }}" },
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 9.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                nowDate.minusDays(data.size.toLong() / 2)
                    .let { it -> "${it.dayOfMonth.let { if (it in 0..9) "0$it" else it }}.${it.monthValue.let { if (it in 0..9) "0$it" else it }}" },
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 9.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                nowDate.let { it -> "${it.dayOfMonth.let { if (it in 0..9) "0$it" else it }}.${it.monthValue.let { if (it in 0..9) "0$it" else it }}" },
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 9.sp
            )
        }
    }
}