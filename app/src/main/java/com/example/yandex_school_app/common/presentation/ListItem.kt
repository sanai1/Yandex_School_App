package com.example.yandex_school_app.common.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yandex_school_app.common.domain.ListItemModelUI

@Composable
fun ListItem(
    itemModelUI: ListItemModelUI,
    modifier: Modifier,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 15.dp)
        ) {
            itemModelUI.picture?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(it, modifier = modifier.size(20.dp))
                }
                Spacer(modifier = modifier.width(15.dp))
            }
            Column {
                Text(
                    itemModelUI.title, style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                itemModelUI.description?.let {
                    Text(
                        it, style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))
            itemModelUI.info?.let {
                Text(it)
            }
            when (itemModelUI.typeListItem) {
                TypeListItem.ARROW -> Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.Gray
                )

                TypeListItem.USUAL -> {}
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        ) {}
    }
}