package com.example.common.presentation.transaction


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.domain.entity.account.Currency
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

@Composable
fun AmountEntering(
    modifier: Modifier,
    currency: Currency,
    updateAmount: (String) -> Unit
) {
    var amount by remember { mutableStateOf("0") }
    var visibleAmountDialog by remember { mutableStateOf(false) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Сумма",
            info = "$amount ${currency.symbol}",
            typeListItem = TypeListItem.USUAL,
        ),
        modifier = modifier,
        onClickContainer = {
            visibleAmountDialog = true
        }
    )
    updateAmount.invoke(amount)
    if (visibleAmountDialog) {
        var nowAmount by remember { mutableStateOf(amount) }
        var nowAmountError by remember { mutableStateOf(false) }
        AlertDialog(
            onDismissRequest = {
                visibleAmountDialog = false
            },
            title = { Text("Обновление суммы") },
            text = {
                OutlinedTextField(
                    value = nowAmount,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            nowAmount = it
                            nowAmountError = false
                        }
                    },
                    label = { Text("Сумма") },
                    isError = nowAmountError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        nowAmountError = nowAmount.isBlank() || nowAmount.startsWith(".")
                        if (nowAmountError.not()) {
                            amount = nowAmount
                            updateAmount.invoke(amount)
                            visibleAmountDialog = false
                        }
                    }
                ) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { visibleAmountDialog = false }) {
                    Text("Отмена")
                }
            }
        )

    }
}