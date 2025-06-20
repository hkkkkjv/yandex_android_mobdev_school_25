package ru.kpfu.itis.ya_financial_manager.presentation.screens.incomes

sealed class IncomesEvent {
    data class OnIncomeClick(val id: Int) : IncomesEvent()
    object Refresh : IncomesEvent()

}