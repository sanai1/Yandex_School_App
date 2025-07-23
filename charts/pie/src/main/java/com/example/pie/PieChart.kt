package com.example.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    radius: Dp = 100.dp,
    animationDuration: Int = 1000
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    val animatables = remember(data) {
        data.map { Animatable(0f) }
    }
    val animatedValues = remember {
        mutableStateListOf<Float>().apply {
            addAll(List(data.size) { 0f })
        }
    }

    LaunchedEffect(data) {
        data.forEachIndexed { index, item ->
            launch {
                animatables[index].animateTo(
                    targetValue = item.value / total * 360f,
                    animationSpec = tween(animationDuration)
                )
                animatedValues[index] = animatables[index].value
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .size(radius * 2f)
                .padding(16.dp)
        ) {
            var startAngle = -90f

            for (i in data.indices) {
                val sweepAngle = animatedValues[i]
                drawArc(
                    color = data[i].color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(size.width, size.height),
                    style = Fill
                )
                startAngle += sweepAngle
            }
        }
    }
}