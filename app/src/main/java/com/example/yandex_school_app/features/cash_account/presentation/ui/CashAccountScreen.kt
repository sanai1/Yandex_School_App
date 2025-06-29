package com.example.yandex_school_app.features.cash_account.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.R
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem
import com.example.yandex_school_app.features.cash_account.presentation.AccountViewModel

@Composable
fun CashAccountScreen(
    modifier: Modifier, viewModel: AccountViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    val account = viewModel.allAccount.collectAsStateWithLifecycle()
    viewModel.updateAllAccount()
    Column {
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = "\uD83D\uDCB0",
                title = account.value.getOrNull(0)?.name ?: "Баланс",
                info = "${
                    account.value.getOrNull(0)?.balance ?: "0"
                } ₽",
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                title = "Валюта",
                info = account.value.getOrNull(0)?.currency,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        Icon(painter = painterResource(R.drawable.diagram), contentDescription = "")
    }
}