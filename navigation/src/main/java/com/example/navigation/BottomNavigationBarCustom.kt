package com.example.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@Composable
fun BottomNavigationBarCustom(navController: NavController, selectedItem: NavigationCustomItem) {
    fun checkSelected(item: NavigationCustomItem): Boolean {
        return if (selectedItem in items.values) selectedItem == item else {
            when (selectedItem) {
                secondaryItems[ScreenName.HISTORY_EXPENSE] -> item == items[ScreenName.EXPENSE]
                secondaryItems[ScreenName.HISTORY_INCOME] -> item == items[ScreenName.INCOME]
                secondaryItems[ScreenName.DETAILS_EXPENSE] -> item == items[ScreenName.EXPENSE]
                secondaryItems[ScreenName.DETAILS_INCOME] -> item == items[ScreenName.INCOME]
                secondaryItems[ScreenName.DETAILS_ACCOUNT] -> item == items[ScreenName.CASH_ACCOUNT]
                secondaryItems[ScreenName.CREATE_ACCOUNT] -> item == items[ScreenName.CASH_ACCOUNT]
                else -> false
            }
        }
    }
    NavigationBar {
        items.forEach { element ->
            val item = element.value
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                        tint = if (selectedItem in items.values) {
                            if (selectedItem == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                        } else {
                            when (selectedItem) {
                                secondaryItems[ScreenName.HISTORY_EXPENSE] -> if (item == items[ScreenName.EXPENSE]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                                secondaryItems[ScreenName.HISTORY_INCOME] -> if (item == items[ScreenName.INCOME]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                                secondaryItems[ScreenName.DETAILS_EXPENSE] -> if (item == items[ScreenName.EXPENSE]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                                secondaryItems[ScreenName.DETAILS_INCOME] -> if (item == items[ScreenName.INCOME]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                                secondaryItems[ScreenName.DETAILS_ACCOUNT] -> if (item == items[ScreenName.CASH_ACCOUNT]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                                secondaryItems[ScreenName.CREATE_ACCOUNT] -> if (item == items[ScreenName.CASH_ACCOUNT]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
                                else -> MaterialTheme.colorScheme.onTertiary
                            }
                        },
                    )
                },
                label = {
                    Text(
                        stringResource(item.title),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary
                ),
                selected = checkSelected(item),
                onClick = {
                    navController.navigate(element.key)
                },
                alwaysShowLabel = true
            )
        }
    }
}