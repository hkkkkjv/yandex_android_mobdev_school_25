package ru.kpfu.itis.ya.ui.screens.history

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.kpfu.itis.ya.ui.common.TransactionType
import java.time.LocalDate

@Immutable
data class HistoryState(
    val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val endDate: LocalDate = LocalDate.now(),
    val totalAmount: String = "0 ₽",
    val transactions: ImmutableList<TransactionUiModel> = persistentListOf(),
    val transactionType: TransactionType = TransactionType.EXPENSES,
    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,
    val error: String? = null,
    val validationError: String? = null,
    val isLoading: Boolean = false
)
