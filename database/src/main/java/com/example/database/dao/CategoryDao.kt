package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.model.CategoryModelDB

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(categoryModelDB: CategoryModelDB)

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<CategoryModelDB>

    @Query("SELECT id FROM categories WHERE remoteId = :remoteId")
    suspend fun getCategoryLocalIdByRemoteId(remoteId: Long): Long

    @Query("SELECT * FROM categories WHERE isIncome = :isIncome")
    suspend fun getCategoryByType(isIncome: Boolean): List<CategoryModelDB>
}