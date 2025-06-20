package ru.kpfu.itis.ya_financial_manager.presentation.screens.incomes

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction

@Immutable
sealed class IncomesState {
    object Loading : IncomesState()
    data class Success(val incomes: ImmutableList<Transaction>) : IncomesState()
    data class Error(val message: String) : IncomesState()
}