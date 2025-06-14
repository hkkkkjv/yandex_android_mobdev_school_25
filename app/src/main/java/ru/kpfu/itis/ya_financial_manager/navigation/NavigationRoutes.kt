package ru.kpfu.itis.ya_financial_manager.navigation

sealed class NavigationRoutes(val route: String) {
    object Splash : NavigationRoutes("splash")
    object Expenses : NavigationRoutes("expenses")
    object Incomes : NavigationRoutes("incomes")
    object Account : NavigationRoutes("account")
    object Articles : NavigationRoutes("categories")
    object Settings : NavigationRoutes("settings")
}