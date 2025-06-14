package ru.kpfu.itis.ya_financial_manager.navigation

sealed class NavigationAction {
    object NavigateToExpenses : NavigationAction()
}