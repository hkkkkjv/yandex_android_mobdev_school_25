package ru.kpfu.itis.ya.ui.navigation

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

/**
 * Composable-функция для нижней панели навигации.
 *
 * Single Responsibility: Отвечает только за отображение и управление нижней панелью навигации между основными экранами.
 */
@Suppress("LongMethod")
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        NavigationRoutes.Expenses,
        NavigationRoutes.Incomes,
        NavigationRoutes.Account,
        NavigationRoutes.Articles,
        NavigationRoutes.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = null) },
                label = { Text(stringResource(item.label)) },
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
