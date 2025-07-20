package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.database.model.AccountModelDB

@Dao
interface AccountDao {
    @Insert
    suspend fun insert(accountModelDB: AccountModelDB)

    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountModelDB>

    @Query("SELECT id FROM accounts WHERE remoteId = :remoteId")
    suspend fun getAccountLocalIdByRemoteId(remoteId: Long): Long

    @Update
    suspend fun update(accountModelDB: AccountModelDB)

    @Query("DELETE FROM accounts WHERE remoteId = :accountId")
    suspend fun delete(accountId: Long)
}