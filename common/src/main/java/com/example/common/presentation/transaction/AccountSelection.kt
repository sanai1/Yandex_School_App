package com.example.common.presentation.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.domain.entity.account.AccountDomain
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSelection(
    modifier: Modifier,
    accountList: List<AccountDomain>,
    updateAccount: (AccountDomain) -> Unit
) {
    var selectedAccount by remember { mutableStateOf(accountList.first()) }
    var visibleBottomSheet by remember { mutableStateOf(false) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Счет",
            info = selectedAccount.name,
            typeListItem = TypeListItem.ARROW,
        ),
        modifier = modifier,
        onClickDetails = {
            visibleBottomSheet = true
        }
    )
    updateAccount.invoke(selectedAccount)
    if (visibleBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { visibleBottomSheet = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                accountList.forEach { it ->
                    ListItem(
                        itemModelUI = ListItemModelUI(
                            title = it.name,
                            info = "${it.balance} ${it.currency.symbol}",
                            typeListItem = TypeListItem.USUAL,
                            payload = it.id.toString()
                        ),
                        modifier = modifier.height(70.dp),
                        onClickContainer = { item ->
                            accountList.firstOrNull { it.id.toString() == item.payload }?.let {
                                selectedAccount = it
                                updateAccount.invoke(it)
                            }
                            visibleBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}