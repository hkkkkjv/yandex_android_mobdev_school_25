package ru.kpfu.itis.ya_financial_manager.presentation.screens.account

import androidx.compose.runtime.Immutable
import ru.kpfu.itis.ya_financial_manager.domain.model.AccountResponse

@Immutable
sealed class AccountState {
    data object Loading : AccountState()
    data class Success(val accountInfo: AccountResponse) : AccountState()
    data class Error(val message: String) : AccountState()
}