package com.example.yandex_school_app.features.cash_account.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.domain.entity.Currency
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.R
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.yandex_school_app.features.cash_account.presentation.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashAccountScreen(
    modifier: Modifier, viewModel: AccountViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    val selectedAccount = viewModel.getSelectedAccount().collectAsStateWithLifecycle()
    val accounts = viewModel.allAccount.collectAsStateWithLifecycle()
    Column {
        var visibleBottomSheet by remember { mutableStateOf(TypeModalBottomSheet.NONE) }
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = "\uD83D\uDCB0",
                title = selectedAccount.value.name,
                info = selectedAccount.value.let {
                    "${it.balance} ${it.currency.symbol}"
                },
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.surface),
            onClickDetails = {
                visibleBottomSheet = TypeModalBottomSheet.ACCOUNT
            }
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                title = "Валюта",
                info = selectedAccount.value.currency.symbol,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.surface),
            onClickDetails = {
                visibleBottomSheet = TypeModalBottomSheet.CURRENCY
            }
        )
        if (visibleBottomSheet != TypeModalBottomSheet.NONE) {
            val sheetState = rememberModalBottomSheetState()
            ModalBottomSheet(
                onDismissRequest = { visibleBottomSheet = TypeModalBottomSheet.NONE },
                sheetState = sheetState
            ) {
                if (visibleBottomSheet == TypeModalBottomSheet.ACCOUNT) {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        accounts.value.forEach { account ->
                            ListItem(
                                itemModelUI = ListItemModelUI(
                                    title = account.name,
                                    info = "${account.balance} ${account.currency.symbol}",
                                    typeListItem = TypeListItem.USUAL,
                                    payload = account.id.toString()
                                ),
                                modifier = modifier.height(70.dp),
                                onClickContainer = {
                                    viewModel.setSelectedAccountById(it.payload)
                                    visibleBottomSheet = TypeModalBottomSheet.NONE
                                }
                            )
                        }
                    }
                } else {
                    Column {
                        Currency.collectionCurrency.forEach { currency ->
                            ListItem(
                                itemModelUI = ListItemModelUI(
                                    picture = currency.symbol,
                                    title = currency.name,
                                    typeListItem = TypeListItem.USUAL
                                ),
                                modifier = modifier.height(70.dp),
                                onClickContainer = { item ->
                                    viewModel.updateCurrencyOnSelectedAccount(Currency.collectionCurrency.find { it.name == item.title }
                                        ?: Currency.collectionCurrency.first())
                                    visibleBottomSheet = TypeModalBottomSheet.NONE
                                }
                            )
                        }
                    }
                }
            }
        }
        Icon(painter = painterResource(R.drawable.diagram), contentDescription = "")
    }
}

enum class TypeModalBottomSheet {
    NONE,
    ACCOUNT,
    CURRENCY
}