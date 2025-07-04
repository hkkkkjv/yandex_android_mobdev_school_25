package ru.kpfu.itis.ya.ui.screens.account_edit

import androidx.compose.runtime.Immutable
import ru.kpfu.itis.ya.ui.common.Currency

@Immutable
sealed class AccountEditState {
    data object Loading : AccountEditState()
    data class Edit(
        val id: Int,
        val name: String,
        val balance: String,
        val currency: Currency,
        val isSaving: Boolean = false,
        val error: String? = null
    ) : AccountEditState()
    data object Success : AccountEditState()
    data class Error(val message: String) : AccountEditState()
}
