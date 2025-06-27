package ru.kpfu.itis.ya.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.ya.ui.common.TransactionType
import ru.kpfu.itis.ya.ui.screens.account.AccountScreen
import ru.kpfu.itis.ya.ui.screens.articles.ArticlesScreen
import ru.kpfu.itis.ya.ui.screens.expenses.ExpensesScreen
import ru.kpfu.itis.ya.ui.screens.history.HistoryScreen
import ru.kpfu.itis.ya.ui.screens.incomes.IncomesScreen
import ru.kpfu.itis.ya.ui.screens.settings.SettingsScreen
import ru.kpfu.itis.ya.ui.screens.splash.SplashScreen

/**
 * Composable-функция для настройки навигации приложения.
 */
@Suppress("LongMethod")
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.Splash.route,
    navigationFlow: Flow<NavigationAction>
) {
    LaunchedEffect(navigationFlow, navController) {
        navigationFlow.collect { action ->
            handleNavigationAction(action, navController)
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationRoutes.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    handleNavigationAction(
                        NavigationAction.NavigateToExpenses,
                        navController
                    )
                }
            )
        }
        composable(NavigationRoutes.Expenses.route) {
            ExpensesScreen()
        }
        composable(NavigationRoutes.Incomes.route) {
            IncomesScreen()
        }
        composable(NavigationRoutes.Account.route) {
            AccountScreen()
        }
        composable(NavigationRoutes.Articles.route) {
            ArticlesScreen()
        }
        composable(NavigationRoutes.Settings.route) {
            SettingsScreen()
        }
        composable(
            route = NavigationRoutes.History.route,
            arguments = listOf(
                navArgument("transactionType") {
                    type = NavType.StringType
                    defaultValue = TransactionType.EXPENSES.name
                }
            )
        ) { backStackEntry ->
            val typeString = backStackEntry.arguments?.getString("transactionType")
                ?: TransactionType.EXPENSES.name
            val transactionType = TransactionType.valueOf(typeString)
            HistoryScreen(transactionType = transactionType)
        }
    }
}

private fun handleNavigationAction(action: NavigationAction, navController: NavHostController) {
    when (action) {
        is NavigationAction.NavigateToExpenses -> {
            navController.navigate(NavigationRoutes.Expenses.route) {
                popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
            }
        }
    }
}
