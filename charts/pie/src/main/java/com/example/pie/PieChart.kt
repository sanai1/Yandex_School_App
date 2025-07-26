package com.example.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun PieChart(
    data: List<PieChartData>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    radius: Dp = 100.dp,
    ringWidth: Dp = 8.dp,
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
            .fillMaxWidth()
            .height(radius * 2.5f)
    ) {
        Canvas(
            modifier = Modifier.size(radius * 2f)
        ) {
            var startAngle = -90f

            for (i in data.indices) {
                val sweepAngle = animatedValues[i]
                drawArc(
                    color = colors[i % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    size = Size(radius.toPx() * 2, radius.toPx() * 2),
                    style = Stroke(ringWidth.toPx(), cap = StrokeCap.Square)
                )
                startAngle += sweepAngle
            }
        }
        Legend(
            data = data,
            radius = radius,
            colors = colors
        )
    }
}

@Composable
private fun Legend(
    data: List<PieChartData>,
    radius: Dp,
    colors: List<Color>
) {
    Column(
        modifier = Modifier.height(radius * 1.6f),
        verticalArrangement = Arrangement.Center
    ) {
        data.take(7).forEachIndexed { index, item ->
            Row(
                modifier = Modifier.width(radius * 1.5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(colors[index % colors.size])
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${item.value.toInt()}%",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = item.name.take(10).let {
                        if (item.name.length > 10) "$it..." else it
                    },
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }
        }
        if (data.size > 7) {
            Text(
                text = "...",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}