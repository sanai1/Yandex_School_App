package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.database.model.TransactionModelDB
import com.example.database.model.TransactionWithRelations
import java.time.LocalDateTime

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transactionModelDB: TransactionModelDB)

    @Transaction
    @Query("SELECT * FROM transactions")
    suspend fun getAllWithRelations(): List<TransactionWithRelations>

    @Query(
        """
        SELECT * FROM transactions
        WHERE transactionDate BETWEEN :startDate AND :endDate
        ORDER BY transactionDate DESC
    """
    )
    suspend fun getAllByPeriod(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<TransactionWithRelations>

    @Update
    suspend fun update(transactionModelDB: TransactionModelDB)

    @Query("DELETE FROM transactions WHERE remoteId = :transactionId")
    suspend fun delete(transactionId: Long)
}