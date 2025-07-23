package com.example.pie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PieChartWithLegend(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    radius: Dp = 100.dp,
    animationDuration: Int = 1000,
    legendTextStyle: TextStyle = TextStyle(fontSize = 14.sp)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            data = data,
            radius = radius,
            animationDuration = animationDuration
        )

        Spacer(modifier = Modifier.height(16.dp))

        Legend(data = data, textStyle = legendTextStyle)
    }
}

@Composable
private fun Legend(
    data: List<PieChartData>,
    textStyle: TextStyle
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(item.color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = item.name,
                        style = textStyle,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (item.description.isNotEmpty()) {
                        Text(
                            text = item.description,
                            style = textStyle.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                fontSize = 12.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${item.value}",
                    style = textStyle,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}