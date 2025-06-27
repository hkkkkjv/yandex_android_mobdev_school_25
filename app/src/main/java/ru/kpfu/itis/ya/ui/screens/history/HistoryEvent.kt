package ru.kpfu.itis.ya.ui.screens.history

import ru.kpfu.itis.ya.ui.common.TransactionType
import java.time.LocalDate

sealed class HistoryEvent {
    data object Refresh : HistoryEvent()
    data object DismissError : HistoryEvent()
    data class SetTransactionType(val type: TransactionType) : HistoryEvent()
    data class SetStartDate(val date: LocalDate) : HistoryEvent()
    data class SetEndDate(val date: LocalDate) : HistoryEvent()
    data object ToggleStartDatePicker : HistoryEvent()
    data object ToggleEndDatePicker : HistoryEvent()
    data object ClearStartDate : HistoryEvent()
    data object ClearEndDate : HistoryEvent()
    data object DismissValidationError : HistoryEvent()
}
