package com.example.cash_account.presentation.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmationDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Подтверждение удаления", color = MaterialTheme.colorScheme.onBackground) },
        text = {
            Text(
                "Вы уверены, что хотите удалить этот элемент?",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
            ) {
                Text("Удалить", color = MaterialTheme.colorScheme.surface)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}