package com.example.cash_account.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.account.AccountDomain

@Composable
fun UpdateNameAndBalanceAccountDialog(
    accountDomain: AccountDomain,
    onConfirm: (AccountDomain) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(accountDomain.name) }
    var amount by remember { mutableStateOf(accountDomain.balance.replace(" ", "")) }
    var nameError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Редактирование счёта") },
        text = {
            Column(Modifier.padding(top = 8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = false
                    },
                    label = { Text("Название счёта") },
                    isError = nameError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (nameError) {
                    Text(
                        text = "Введите название",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            amount = it
                            amountError = false
                        }
                    },
                    label = { Text("Сумма") },
                    isError = amountError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )
                if (amountError) {
                    Text(
                        text = "Введите корректную сумму",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    nameError = name.isBlank()
                    amountError = amount.isBlank() || amount.startsWith(".")

                    if (!nameError && !amountError) {
                        onConfirm(accountDomain.copy(name = name, balance = amount))
                    }
                }
            ) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}