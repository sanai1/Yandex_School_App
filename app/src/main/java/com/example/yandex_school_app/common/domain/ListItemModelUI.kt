package com.example.yandex_school_app.common.domain

import com.example.yandex_school_app.common.presentation.TypeListItem

data class ListItemModelUI(
    val picture: String?,
    val title: String,
    val description: String?,
    val info: String?,
    val typeListItem: TypeListItem,
)
