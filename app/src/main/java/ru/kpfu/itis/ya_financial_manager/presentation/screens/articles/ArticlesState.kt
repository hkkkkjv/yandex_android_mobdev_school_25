package ru.kpfu.itis.ya_financial_manager.presentation.screens.articles

import androidx.compose.runtime.Immutable
import ru.kpfu.itis.ya_financial_manager.domain.model.AccountResponse

@Immutable
sealed class ArticlesState {
    data object Loading : ArticlesState()
    data class Success(val accountInfo: AccountResponse) : ArticlesState()
    data class Error(val message: String) : ArticlesState()
}