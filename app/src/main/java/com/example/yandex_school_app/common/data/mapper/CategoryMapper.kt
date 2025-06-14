package com.example.yandex_school_app.common.data.mapper

import com.example.yandex_school_app.features.category.domain.CategoryDomain
import com.example.yandex_school_app.common.data.CategoryNetwork

class CategoryMapper {
    fun toCategoryDomain(categoryNetwork: CategoryNetwork) = CategoryDomain(
        name = categoryNetwork.name,
        emoji = categoryNetwork.emoji
    )
}