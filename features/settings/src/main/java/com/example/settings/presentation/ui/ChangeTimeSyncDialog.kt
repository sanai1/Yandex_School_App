package com.example.settings.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ChangeTimeSyncDialog(
    dialogState: MutableState<Boolean>,
    currentHours: Long,
    onChangeNewHours: (Long) -> Unit
) {
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        title = {
            Text(
                "Выберите период синхронизации данных",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        text = {
            val hourSteps = listOf(1L, 2L, 4L, 6L, 12L, 24L)
            val currentStepIndex = remember {
                mutableIntStateOf(
                    hourSteps.indexOfFirst { it >= currentHours }.coerceAtLeast(0)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Slider(
                    value = currentStepIndex.intValue.toFloat(),
                    onValueChange = { newValue ->
                        val newIndex = newValue.roundToInt().coerceIn(0, hourSteps.size - 1)
                        currentStepIndex.intValue = newIndex
                        onChangeNewHours(hourSteps[newIndex])
                    },
                    valueRange = 0f..(hourSteps.size - 1).toFloat(),
                    steps = hourSteps.size - 2,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Выбрано: ${hourSteps[currentStepIndex.intValue]} ч.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Варианты: ${hourSteps.joinToString()}",
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.Gray
                )
            }
        },
        confirmButton = {}
    )
}