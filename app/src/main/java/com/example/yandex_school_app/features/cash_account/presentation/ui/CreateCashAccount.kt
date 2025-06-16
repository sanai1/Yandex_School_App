package com.example.yandex_school_app.features.cash_account.presentation.ui

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.common.presentation.ToastController
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.features.cash_account.domain.entity.Currency
import com.example.yandex_school_app.features.cash_account.presentation.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCashAccount(
    navController: NavController,
    modifier: Modifier,
    isAddAccountClicked: MutableState<Boolean>,
    callback: () -> Unit,
    viewModel: AccountViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    val name = remember { mutableStateOf("") }
    val balance = remember { mutableStateOf("") }
    val selectedCurrency = remember { mutableStateOf(Currency.RUB) }
    LaunchedEffect(isAddAccountClicked.value) {
        if (isAddAccountClicked.value) {
            viewModel.createCashAccount(
                AccountDomain(
                    id = 0,
                    name = name.value,
                    balance = balance.value,
                    currency = selectedCurrency.value
                )
            )
            navController.popBackStack()
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
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
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
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),
            modifier = modifier.fillMaxWidth()
        )
        val expanded = remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = expanded.value.not() },
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TextField(
                modifier = modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedCurrency.value,
                onValueChange = {},
                label = {
                    Text(
                        "Выберите валюту счета",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                listOf(Currency.RUB, Currency.USD, Currency.EUR).forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
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