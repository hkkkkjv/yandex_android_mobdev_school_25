package ru.kpfu.itis.ya_financial_manager.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.kpfu.itis.ya_financial_manager.R

sealed class BottomNavItem(
    val route: String,
    val iconResId: Int,
    val titleResId: Int
) {
    object Expenses : BottomNavItem(
        route = NavigationRoutes.Expenses.route,
        iconResId = R.drawable.ic_expenses_24,
        titleResId = R.string.expenses
    )
    object Incomes : BottomNavItem(
        route = NavigationRoutes.Incomes.route,
        iconResId = R.drawable.ic_incomes_24,
        titleResId = R.string.incomes
    )
    object Account : BottomNavItem(
        route = NavigationRoutes.Account.route,
        iconResId = R.drawable.ic_account_24,
        titleResId = R.string.account
    )
    object Articles : BottomNavItem(
        route = NavigationRoutes.Articles.route,
        iconResId = R.drawable.ic_articles_24,
        titleResId = R.string.articles
    )
    object Settings : BottomNavItem(
        route = NavigationRoutes.Settings.route,
        iconResId = R.drawable.ic_settings_24,
        titleResId = R.string.settings
    )
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Expenses,
        BottomNavItem.Incomes,
        BottomNavItem.Account,
        BottomNavItem.Articles,
        BottomNavItem.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.iconResId), contentDescription = null) },
                label = { Text(stringResource(item.titleResId)) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}