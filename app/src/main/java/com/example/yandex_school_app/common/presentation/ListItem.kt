package com.example.yandex_school_app.common.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItem(
    itemModelUI: ListItemModelUI,
    modifier: Modifier,
    onClickDate: ((String) -> Unit)? = null
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 15.dp)
        ) {
            itemModelUI.picture?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(it, modifier = modifier.size(20.dp))
                }
                Spacer(modifier = modifier.width(15.dp))
            }
            Column {
                Text(
                    itemModelUI.title, style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                itemModelUI.description?.let {
                    Text(
                        it, style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))
            itemModelUI.info?.let {
                val info = remember { mutableStateOf(it) }
                val showDatePicker = remember { mutableStateOf(false) }
                TextButton(onClick = {
                    onClickDate?.run { showDatePicker.value = true }
                }) {
                    Text(info.value, color = MaterialTheme.colorScheme.onSurface)
                }
                if (showDatePicker.value) {
                    val datePickerState = rememberDatePickerState()
                    val dateFormatter = remember {
                        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    }
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker.value = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        info.value = dateFormatter.format(Date(millis))
                                        onClickDate?.invoke(info.value)
                                    }
                                    showDatePicker.value = false
                                }
                            ) {
                                Text("ОК")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker.value = false }) {
                                Text("Отмена")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
            when (itemModelUI.typeListItem) {
                TypeListItem.ARROW -> Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.Gray
                )

                TypeListItem.SWITCH -> Switch(
                    checked = false,
                    onCheckedChange = {},
                )

                TypeListItem.USUAL -> {}
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        ) {}
    }
}