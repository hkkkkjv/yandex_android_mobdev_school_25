package ru.kpfu.itis.ya_financial_manager.presentation.screens.expenses

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction
@Immutable
sealed class ExpensesState {
    data object Loading : ExpensesState()
    data class Success(val expenses: ImmutableList<Transaction>) : ExpensesState()
    data class Error(val message: String) : ExpensesState()
}