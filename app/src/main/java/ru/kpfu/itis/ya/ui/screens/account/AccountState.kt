package ru.kpfu.itis.ya.ui.screens.account

import androidx.compose.runtime.Immutable
import ru.kpfu.itis.ya.domain.model.AccountResponse

@Immutable
sealed class AccountState {
    data object Loading : AccountState()
    data class Success(val accountInfo: AccountResponse) : AccountState()
    data class Error(val message: String) : AccountState()
}
