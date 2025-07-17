package com.example.common.data.mapper

import com.example.common.domain.entity.category.CategoryDomain
import com.example.database.model.CategoryModelDB
import com.example.network.model.category.CategoryNetwork
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toCategoryDomain(categoryNetwork: CategoryNetwork) = CategoryDomain(
        id = categoryNetwork.id,
        localId = 0,
        name = categoryNetwork.name,
        emoji = categoryNetwork.emoji,
        isIncome = categoryNetwork.isIncome
    )

    fun toCategoryModelDB(categoryDomain: CategoryDomain) = CategoryModelDB(
        id = categoryDomain.localId,
        remoteId = categoryDomain.id.toLong(),
        name = categoryDomain.name,
        emoji = categoryDomain.emoji ?: "",
        isIncome = categoryDomain.isIncome
    )

    fun toCategoryDomain(categoryModelDB: CategoryModelDB) = CategoryDomain(
        id = categoryModelDB.remoteId.toInt(),
        localId = categoryModelDB.id,
        name = categoryModelDB.name,
        emoji = categoryModelDB.emoji,
        isIncome = categoryModelDB.isIncome
    )
}