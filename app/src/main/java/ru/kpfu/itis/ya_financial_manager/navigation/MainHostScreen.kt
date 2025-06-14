package ru.kpfu.itis.ya_financial_manager.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainHostScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationRoutes.Splash.route,
    appNavigator: AppNavigator
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val doNotShowBottomBar = currentRoute in listOf(
        NavigationRoutes.Splash.route
    )
    Scaffold(
        bottomBar = {
            if (!doNotShowBottomBar) {
                BottomNavigation(
                    navController = navController
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AppNavigation(
                navController = navController,
                startDestination = startDestination,
                navigationFlow = appNavigator.navigationAction
            )
        }
    }
}