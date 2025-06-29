package com.example.yandex_school_app.common.data.mapper

import com.example.yandex_school_app.common.domain.entity.CategoryDomain
import com.example.yandex_school_app.common.data.network.model.CategoryNetwork
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toCategoryDomain(categoryNetwork: CategoryNetwork) = CategoryDomain(
        name = categoryNetwork.name,
        emoji = categoryNetwork.emoji,
        isIncome = categoryNetwork.isIncome
    )
}