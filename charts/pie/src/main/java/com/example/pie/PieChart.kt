package com.example.pie

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    data: List<PieChartData>,
    radiusOuter: Float = 90f,
    chartBarWidth: Float = 30f,
    animDuration: Int = 1000,
) {
    val total = data.sumOf { it.value.toDouble() }
    val floatValue = mutableListOf<Float>()

    data.forEachIndexed { index, _ ->
        floatValue.add(index, 0f)
    }

    var animationPlayed by remember { mutableStateOf(false) }
    var currentValue = 0f
    val size = (radiusOuter * 2f).dp

    LaunchedEffect(key1 = true) {
        animationPlayed = true
        data.forEachIndexed { index, pieData ->
            animate(
                initialValue = 0f,
                targetValue = (pieData.value / total.toFloat()) * 360f,
                animationSpec = tween(
                    durationMillis = animDuration,
                    delayMillis = index * (animDuration / 3),
                    easing = LinearOutSlowInEasing
                )
            ) { value, _ ->
                floatValue[index] = value
                currentValue = value
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            var startAngle = -90f

            data.forEachIndexed { index, pieData ->
                drawArc(
                    color = pieData.color,
                    startAngle = startAngle,
                    sweepAngle = floatValue[index],
                    useCenter = false,
                    style = Stroke(
                        width = chartBarWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    ),
                    size = Size(
                        width = size.toPx(),
                        height = size.toPx()
                    )
                )
                startAngle += floatValue[index]
            }
        }
    }
}