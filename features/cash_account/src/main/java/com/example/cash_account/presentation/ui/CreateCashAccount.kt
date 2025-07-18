package com.example.cash_account.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.common.presentation.toast.ToastController
import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.account.Currency
import com.example.cash_account.presentation.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCashAccount(
    modifier: Modifier,
    isAddAccountClicked: MutableState<Boolean>,
    callbackNavController: () -> Unit,
    callback: () -> Unit,
    viewModel: AccountViewModel
) {
    val name = remember { mutableStateOf("") }
    val balance = remember { mutableStateOf("") }
    val selectedCurrency = remember { mutableStateOf(Currency.collectionCurrency.first()) }
    LaunchedEffect(isAddAccountClicked.value) {
        if (isAddAccountClicked.value) {
            if (name.value.isEmpty()) {
                ToastController.showToast("Введите название счета")
            } else if (balance.value.isEmpty()) {
                ToastController.showToast("Введите баланс счета")
            } else {
                viewModel.createCashAccount(
                    AccountDomain(
                        id = 0,
                        localId = 0,
                        name = name.value,
                        balance = balance.value,
                        currency = selectedCurrency.value
                    )
                )
                callbackNavController.invoke()
            }
        }
        callback.invoke()
    }
    Column {
        TextField(
            value = name.value,
            onValueChange = {
                if (it.length < 50) name.value = it
                else ToastController.showToast("Максимальная длина 50 символов")
            },
            placeholder = { Text("Введите название счета") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = modifier.fillMaxWidth()
        )
        TextField(
            value = balance.value,
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*$"))) balance.value =
                    it else ToastController.showToast("Неверный формат ввода")
            },
            placeholder = { Text("Введите баланс") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = modifier.fillMaxWidth()
        )
        val expanded = remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = expanded.value.not() },
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            TextField(
                modifier = modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedCurrency.value.name,
                onValueChange = {},
                label = {
                    Text(
                        "Выберите валюту счета",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                Currency.collectionCurrency.forEach { currency ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                currency.name,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        onClick = {
                            selectedCurrency.value = currency
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}