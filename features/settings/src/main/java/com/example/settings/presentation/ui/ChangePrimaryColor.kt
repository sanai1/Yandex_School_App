package com.example.settings.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.common.store.AppTheme
import com.example.common.store.AppTheme.toColor

@Composable
fun ChangePrimaryColor(
    dialogState: MutableState<Boolean>,
    onChangeColor: (AppTheme.PrimaryColorVariant) -> Unit
) {
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        title = { Text("Выберите цвет", color = MaterialTheme.colorScheme.onBackground) },
        text = {
            Column {
                AppTheme.PrimaryColorVariant.entries.forEach { variant ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onChangeColor.invoke(variant)
                                dialogState.value = false
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(variant.toColor())
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(variant.title)
                    }
                }
            }
        },
        confirmButton = {}
    )
}