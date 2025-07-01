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
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
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
                title = viewModel.getSelectedAccount().name,
                info = viewModel.getSelectedAccount().let {
                    "${it.balance} ${it.currency.symbol}"
                },
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        account.value
        ListItem(
            itemModelUI = ListItemModelUI(
                title = "Валюта",
                info = viewModel.getSelectedAccount().currency.abbreviation,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        Icon(painter = painterResource(R.drawable.diagram), contentDescription = "")
    }
}