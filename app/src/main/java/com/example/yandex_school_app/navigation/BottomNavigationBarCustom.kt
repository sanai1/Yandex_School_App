package com.example.yandex_school_app.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@Composable
fun BottomNavigationBarCustom(navController: NavController, selectedItem: NavigationCustomItem) {
    NavigationBar {
        items.forEach { element ->
            val item = element.value
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                        tint = if (selectedItem in items.values) {
                            if (selectedItem == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        } else {
                            when (selectedItem) {
                                secondaryItems[ScreenName.HISTORY_EXPENSE] -> if (item == items[ScreenName.EXPENSE]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                secondaryItems[ScreenName.HISTORY_INCOME] -> if (item == items[ScreenName.INCOME]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.secondary
                            }
                        },
                    )
                },
                label = { Text(stringResource(item.title)) },
                selected = if (selectedItem in items.values) selectedItem == item else {
                    when (selectedItem) {
                        secondaryItems[ScreenName.HISTORY_EXPENSE] -> item == items[ScreenName.EXPENSE]
                        secondaryItems[ScreenName.HISTORY_INCOME] -> item == items[ScreenName.INCOME]
                        else -> false
                    }
                },
                onClick = {
                    navController.navigate(element.key)
                },
                alwaysShowLabel = true
            )
        }
    }
}