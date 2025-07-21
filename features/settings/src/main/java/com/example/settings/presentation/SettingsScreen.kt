package com.example.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.common.store.AppTheme

@Composable
fun SettingsScreen(
    modifier: Modifier,
    viewmodel: SettingsViewmodel,
    onChangeTheme: (Boolean) -> Unit,
    onChangePrimaryColor: (AppTheme.PrimaryColorVariant) -> Unit
) {
    val showSetPinScreen = remember { mutableStateOf(false) }
    if (showSetPinScreen.value) SetPinScreen(
        pinManager = viewmodel.getPinManager(),
        showSetPinScreen
    )
    else Column {
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
            isDarkTheme = viewmodel.isDarkTheme
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
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Вибрация",
                description = null,
                info = null,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.height(56.dp)
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Код пароль",
                description = null,
                info = null,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.height(56.dp),
            onClickDetails = {
                showSetPinScreen.value = true
            }
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Синхронизация",
                description = null,
                info = null,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.height(56.dp)
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Язык",
                description = null,
                info = null,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.height(56.dp)
        )
        val showDialogBuildConfig = remember { mutableStateOf(false) }
        if (showDialogBuildConfig.value) BuildConfigDialog(showDialogBuildConfig)
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "О программе",
                description = null,
                info = null,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.height(56.dp),
            onClickDetails = {
                showDialogBuildConfig.value = true
            }
        )
    }
}