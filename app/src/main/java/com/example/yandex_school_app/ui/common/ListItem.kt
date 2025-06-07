package com.example.yandex_school_app.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    picture: ImageVector?,
    title: String,
    description: String?,
    info: String?,
    modifier: Modifier,
    click: @Composable (() -> Unit)?
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(56.dp)) {
        picture?.let {
            Icon(imageVector = picture, contentDescription = "")
        }
        Column {
            Text(title)
            description?.let {
                Text(it)
            }
        }
        info?.let {
            Text(it)
        }
        click?.invoke()
    }
}