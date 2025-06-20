package ru.kpfu.itis.ya_financial_manager.presentation.screens.expenses

sealed class ExpensesEvent {
    data class OnExpenseClick(val id: Int) : ExpensesEvent()
    data object Refresh : ExpensesEvent()
}