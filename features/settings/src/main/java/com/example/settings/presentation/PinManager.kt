package com.example.settings.presentation

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import androidx.core.content.edit
import com.example.common.store.NamedStore

class PinManager @Inject constructor(context: Context) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        SECURITY,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun isPinSet(): Boolean = sharedPreferences.getString(NamedStore.PIN_CODE, null) != null

    fun validatePin(inputPin: String): Boolean {
        val savedHash = sharedPreferences.getString(NamedStore.PIN_CODE, null) ?: return false
        return hashPin(inputPin) == savedHash
    }

    fun setNewPin(pin: String) {
        sharedPreferences.edit {
            putString(NamedStore.PIN_CODE, hashPin(pin))
        }
    }

    fun pinIsSet(): Boolean {
        return sharedPreferences.getString(NamedStore.PIN_CODE, null) != null
    }

    private fun hashPin(pin: String): String {
        return pin.hashCode().toString()
    }

    fun clearPin() {
        sharedPreferences.edit { remove(NamedStore.PIN_CODE) }
    }

    companion object {
        const val SECURITY = "security"
    }
}