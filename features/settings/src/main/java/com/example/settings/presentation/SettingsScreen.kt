package com.example.settings.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.common.store.AppTheme
import com.example.common.store.AppTheme.toColor


@Composable
fun SettingsScreen(
    modifier: Modifier,
    viewmodel: SettingsViewmodel,
    onChangeTheme: (Boolean) -> Unit,
    onChangePrimaryColor: (AppTheme.PrimaryColorVariant) -> Unit
) {
    val listTitles = listOf(
        "Звуки",
        "Хаптики",
        "Код пароль",
        "Синхронизация",
        "Язык",
        "О программе"
    )
    val isDarkTheme by viewmodel.isDarkTheme.collectAsStateWithLifecycle()
    Column {
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Тёмная тема",
                description = null,
                info = null,
                typeListItem = TypeListItem.SWITCH
            ),
            modifier = modifier.height(56.dp),
            onClickChangeTheme = { isDark ->
                viewmodel.setTheme(isDark)
                onChangeTheme.invoke(isDark)
            },
            isDarkTheme = isDarkTheme
        )
        val showDialogChangePrimaryColor = remember { mutableStateOf(false) }
        if (showDialogChangePrimaryColor.value) ChangePrimaryColor(showDialogChangePrimaryColor) { variant ->
            viewmodel.setPrimaryColor(variant)
            onChangePrimaryColor.invoke(variant)
        }
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Основной цвет",
                description = null,
                info = null,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.height(56.dp),
            onClickDetails = {
                showDialogChangePrimaryColor.value = true
            }
        )
        listTitles.forEach {
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = null,
                    title = it,
                    description = null,
                    info = null,
                    typeListItem = TypeListItem.ARROW
                ),
                modifier = modifier.height(56.dp),
                onClickChangeTheme = { isDark ->
                    viewmodel.setTheme(isDark)
                    onChangeTheme.invoke(isDark)
                },
                isDarkTheme = isDarkTheme
            )
        }
    }
}

@Composable
private fun ChangePrimaryColor(
    dialogState: MutableState<Boolean>,
    onChangeColor: (AppTheme.PrimaryColorVariant) -> Unit
) {
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        title = { Text("Выберите цвет", color = MaterialTheme.colorScheme.onBackground) },
        text = {
            Column {
                AppTheme.PrimaryColorVariant.entries.forEach { variant ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onChangeColor.invoke(variant)
                                dialogState.value = false
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(variant.toColor())
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(variant.title)
                    }
                }
            }
        },
        confirmButton = {}
    )
}

























