package ru.kpfu.itis.ya.ui.navigation

/**
 * Sealed класс для определения навигационных действий.
 *
 * Single Responsibility: Отвечает только за определение типов навигационных действий в приложении.
 */
sealed class NavigationAction {
    data object NavigateToExpenses : NavigationAction()
}
