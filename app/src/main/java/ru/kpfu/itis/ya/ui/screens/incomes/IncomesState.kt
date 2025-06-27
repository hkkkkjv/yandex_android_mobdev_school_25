package ru.kpfu.itis.ya.ui.screens.incomes

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import ru.kpfu.itis.ya.domain.model.Transaction

@Immutable
sealed class IncomesState {
    data object Loading : IncomesState()
    data class Success(val incomes: ImmutableList<Transaction>) : IncomesState()
    data class Error(val message: String) : IncomesState()
}
