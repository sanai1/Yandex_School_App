package com.example.common.store

import com.example.common.domain.entity.transaction.TransactionDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TransactionStore @Inject constructor() {

    fun setSelectedTransaction(selectedTransaction: TransactionDomain) {
        _selectedTransaction.value = selectedTransaction
    }

    companion object {
        private val _selectedTransaction = MutableStateFlow<TransactionDomain?>(null)
        val selectedTransaction: StateFlow<TransactionDomain?> = _selectedTransaction.asStateFlow()
    }
}