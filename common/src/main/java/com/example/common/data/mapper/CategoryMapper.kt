package com.example.common.data.mapper

import com.example.common.domain.entity.CategoryDomain
import com.example.network.model.CategoryNetwork
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toCategoryDomain(categoryNetwork: CategoryNetwork) = CategoryDomain(
        name = categoryNetwork.name,
        emoji = categoryNetwork.emoji,
        isIncome = categoryNetwork.isIncome
    )
}