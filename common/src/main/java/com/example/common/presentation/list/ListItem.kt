package com.example.common.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.domain.entity.ListItemModelUI
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun ListItem(
    itemModelUI: ListItemModelUI,
    modifier: Modifier,
    isAnalytics: Boolean = false,
    onClickContainer: ((ListItemModelUI) -> Unit)? = null,
    onClickDate: ((String) -> Unit)? = null,
    onClickTime: ((String) -> Unit)? = null,
    onClickDetails: (() -> Unit)? = null,
    onClickChangeTheme: (Boolean) -> Unit = {},
    isDarkTheme: Boolean = false
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onClickContainer?.invoke(itemModelUI)
                }
                .padding(horizontal = 15.dp)
        ) {
            itemModelUI.picture?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(it, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.size(15.dp))
            }
            itemModelUI.icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(15.dp))
            }
            Column {
                Text(
                    itemModelUI.title,
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    color = if (itemModelUI.isHint) Color.LightGray else MaterialTheme.colorScheme.onBackground
                )
                itemModelUI.description?.let {
                    Text(
                        it, style = TextStyle(
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
            )
            Column(
                horizontalAlignment = Alignment.End
            ) {
                itemModelUI.info?.let {
                    if (onClickDate != null) {
                        TextButtonDate(it, isAnalytics, onClickDate)
                    } else if (onClickTime != null) {
                        TextButtonTime(it, onClickTime)
                    } else {
                        Text(it, color = MaterialTheme.colorScheme.onBackground)
                    }
                }
                itemModelUI.infoDescription?.let {
                    Text(it, color = MaterialTheme.colorScheme.onTertiary)
                }
            }
            when (itemModelUI.typeListItem) {
                TypeListItem.ARROW -> {
                    IconButton(onClick = { onClickDetails?.invoke() }) {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }

                TypeListItem.SWITCH -> {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = onClickChangeTheme,
                    )
                }

                TypeListItem.USUAL -> {}
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextButtonDate(info: String, isAnalytics: Boolean, onClickDate: ((String) -> Unit)?) {
    val showDatePicker = remember { mutableStateOf(false) }
    if (isAnalytics) {
        val formattingDate = fun(dateString: String): String {
            val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            val mapMonth = mapOf(
                1 to "январь",
                2 to "февраль",
                3 to "март",
                4 to "апрель",
                5 to "май",
                6 to "июнь",
                7 to "июль",
                8 to "август",
                9 to "сентябрь",
                10 to "октябрь",
                11 to "ноябрь",
                12 to "декабрь"
            )
            return "${mapMonth[date.monthValue]} ${date.year}"
        }
        Button(
            onClick = {
                onClickDate?.run { showDatePicker.value = true }
            }
        ) {
            Text(formattingDate.invoke(info), color = MaterialTheme.colorScheme.onBackground)
        }
    } else {
        TextButton(
            onClick = {
                onClickDate?.run { showDatePicker.value = true }
            }
        ) {
            Text(info, color = MaterialTheme.colorScheme.onBackground)
        }
    }
    if (showDatePicker.value) {
        val dateFormatter = remember {
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = (if (info == "сегодня") LocalDate.now() else LocalDate.parse(
                info,
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )).atStartOfDay(
                ZoneOffset.UTC
            ).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onClickDate?.invoke(dateFormatter.format(Date(millis)))
                        }
                        showDatePicker.value = false
                    }
                ) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
                    Text("Отмена", color = Color.Red)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.secondary,
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    headlineContentColor = MaterialTheme.colorScheme.onSecondary,
                    subheadContentColor = MaterialTheme.colorScheme.onSecondary,
                    navigationContentColor = MaterialTheme.colorScheme.onSecondary,
                    weekdayContentColor = MaterialTheme.colorScheme.onSecondary,
                    dayContentColor = MaterialTheme.colorScheme.onSecondary,
                    selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    dateTextFieldColors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextButtonTime(info: String, onClickTime: ((String) -> Unit)?) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(
        initialHour = info.substring(0, 2).toInt(),
        initialMinute = info.substring(3).toInt(),
        is24Hour = true
    )
    TextButton(onClick = {
        onClickTime?.run { showTimePicker = true }
    }) {
        Text(info, color = MaterialTheme.colorScheme.onBackground)
    }
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Выберите время") },
            text = {
                TimePicker(
                    state = timeState,
                    colors = TimePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                        clockDialColor = MaterialTheme.colorScheme.secondary,
                        selectorColor = MaterialTheme.colorScheme.primary,
                        clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.secondary,
                        timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.tertiary,
                        timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onSecondary,
                        timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onTertiary
                    )
                )
            },
            confirmButton = {
                Button(onClick = {
                    onClickTime?.invoke(
                        "${
                            timeState.hour.let {
                                if (it in 0..9) "0$it" else it
                            }
                        }:${
                            timeState.minute.let {
                                if (it in 0..9) "0$it" else it
                            }
                        }"
                    )
                    showTimePicker = false
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Отмена", color = Color.Red)
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    }
}