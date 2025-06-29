package com.example.common.domain.entity

import com.example.common.presentation.list.TypeListItem


data class ListItemModelUI(
    val picture: String? = null,
    val title: String,
    val description: String? = null,
    val info: String? = null,
    val infoDescription: String? = null,
    val typeListItem: TypeListItem,
)
