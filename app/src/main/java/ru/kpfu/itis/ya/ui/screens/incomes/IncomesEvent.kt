package ru.kpfu.itis.ya.ui.screens.incomes

sealed class IncomesEvent {
    data class OnIncomeClick(val id: Int) : IncomesEvent()
    data object Refresh : IncomesEvent()
}
