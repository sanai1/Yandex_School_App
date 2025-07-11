package com.example.yandex_school_app.di.module

import androidx.lifecycle.ViewModel
import com.example.yandex_school_app.di.ViewModelKey
import com.example.cash_account.presentation.AccountViewModel
import com.example.category.presentation.CategoryViewModel
import com.example.expense.presentation.ExpenseViewModel
import com.example.income.presentation.IncomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    @Binds
    fun bindCategoryViewModel(impl: CategoryViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ExpenseViewModel::class)
    @Binds
    fun bindExpenseViewModel(impl: ExpenseViewModel): ViewModel

    @IntoMap
    @ViewModelKey(IncomeViewModel::class)
    @Binds
    fun bindIncomeViewModel(impl: IncomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    @Binds
    fun bindAccountViewModel(impl: AccountViewModel): ViewModel
}