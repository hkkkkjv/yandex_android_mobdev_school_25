package ru.kpfu.itis.ya_financial_manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.ya_financial_manager.presentation.screens.account.AccountScreen
import ru.kpfu.itis.ya_financial_manager.presentation.screens.articles.ArticlesScreen
import ru.kpfu.itis.ya_financial_manager.presentation.screens.expenses.ExpensesScreen
import ru.kpfu.itis.ya_financial_manager.presentation.screens.incomes.IncomesScreen
import ru.kpfu.itis.ya_financial_manager.presentation.screens.settings.SettingsScreen
import ru.kpfu.itis.ya_financial_manager.presentation.screens.splash.SplashScreen

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
    }
}

private fun handleNavigationAction(action: NavigationAction, navController: NavHostController) {
    when (action) {
        is NavigationAction.NavigateToExpenses -> {
            navController.navigate(NavigationRoutes.Expenses.route) {
                popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
            }
        }

        else -> {}
    }
}