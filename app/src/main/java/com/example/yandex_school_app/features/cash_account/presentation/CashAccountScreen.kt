package com.example.yandex_school_app.features.cash_account.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.yandex_school_app.R
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun CashAccountScreen(accountDomain: AccountDomain, modifier: Modifier) {
    val account = remember { mutableStateOf(accountDomain) }
    Column {
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = "\uD83D\uDCB0",
                title = "Баланс",
                description = null,
                info = account.value.balance,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Валюта",
                description = null,
                info = account.value.currency,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        Icon(painter = painterResource(R.drawable.diagram), contentDescription = "")
    }
}