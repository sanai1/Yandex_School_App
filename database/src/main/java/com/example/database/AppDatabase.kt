package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.dao.AccountDao
import com.example.database.dao.CategoryDao
import com.example.database.dao.TransactionDao
import com.example.database.model.AccountModelDB
import com.example.database.model.CategoryModelDB
import com.example.database.model.TransactionModelDB

@Database(
    version = 1,
    entities = [
        CategoryModelDB::class,
        AccountModelDB::class,
        TransactionModelDB::class
    ],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
    }
}