package com.example.yandex_school_app.common.domain.entity

import com.example.yandex_school_app.common.presentation.TypeListItem

data class ListItemModelUI(
    val picture: String? = null,
    val title: String,
    val description: String? = null,
    val info: String? = null,
    val infoDescription: String? = null,
    val typeListItem: TypeListItem,
)
