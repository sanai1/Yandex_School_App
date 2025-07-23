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
import androidx.compose.ui.unit.dp

@Composable
fun PieChartWithLegend(
    data: List<PieChartData>,
    radiusOuter: Float = 90f,
    chartBarWidth: Float = 30f,
    animDuration: Int = 1000,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            data = data,
            radiusOuter = radiusOuter,
            chartBarWidth = chartBarWidth,
            animDuration = animDuration
        )

        Spacer(modifier = Modifier.height(16.dp))

        Legend(data)
    }
}

@Composable
private fun Legend(
    data: List<PieChartData>
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
                Text(
                    text = "${item.name}: ${item.value}",
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (item.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.description,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}