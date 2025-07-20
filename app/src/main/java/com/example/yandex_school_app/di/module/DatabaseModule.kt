package com.example.yandex_school_app.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.database.AppDatabase
import com.example.database.dao.AccountDao
import com.example.database.dao.CategoryDao
import com.example.database.dao.TransactionDao
import com.example.yandex_school_app.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {
    @Provides
    @ApplicationScope
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @ApplicationScope
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_THEME, Context.MODE_PRIVATE)
    }

    @Provides
    @ApplicationScope
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @ApplicationScope
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao {
        return appDatabase.accountDao()
    }

    @Provides
    @ApplicationScope
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao {
        return appDatabase.transactionDao()
    }

    private const val APP_THEME = "app_theme"
}