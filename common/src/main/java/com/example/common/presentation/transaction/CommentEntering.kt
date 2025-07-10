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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

@Composable
fun CommentEntering(
    modifier: Modifier,
    enterComment: String,
    updateComment: (String) -> Unit
) {
    val hint = "Введите комментарий"
    var comment by remember { mutableStateOf(enterComment.let { it.ifEmpty { hint } }) }
    var visibleCommentDialog by remember { mutableStateOf(false) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = comment,
            isHint = comment == hint,
            typeListItem = TypeListItem.USUAL,
        ),
        modifier = modifier,
        onClickContainer = { item ->
            visibleCommentDialog = true
        }
    )
    if (visibleCommentDialog) {
        var nowComment by remember {
            mutableStateOf(
                if (comment == hint) "" else comment
            )
        }
        AlertDialog(
            onDismissRequest = {
                visibleCommentDialog = false
            },
            title = { Text("Обновление комментария") },
            text = {
                OutlinedTextField(
                    value = nowComment,
                    onValueChange = {
                        nowComment = it
                    },
                    label = { Text("Комментарий") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nowComment.isEmpty()) {
                            comment = hint
                        } else {
                            comment = nowComment
                            updateComment.invoke(comment)
                        }
                        visibleCommentDialog = false
                    }
                ) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { visibleCommentDialog = false }) {
                    Text("Отмена", color = Color.Red)
                }
            }
        )

    }
}