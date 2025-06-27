package ru.kpfu.itis.ya.ui.screens.expenses

sealed class ExpensesEvent {
    data class OnExpenseClick(val id: Int) : ExpensesEvent()
    data object Refresh : ExpensesEvent()
}
