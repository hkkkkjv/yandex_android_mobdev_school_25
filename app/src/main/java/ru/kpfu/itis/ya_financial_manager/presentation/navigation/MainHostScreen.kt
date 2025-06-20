package ru.kpfu.itis.ya_financial_manager.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.ya_financial_manager.presentation.common.TopBarViewModel
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.FabAddButton
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.TopAppBar

@Composable
fun MainHostScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationRoutes.Splash.route,
    appNavigator: AppNavigator,
    topBarViewModel: TopBarViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Log.i("MainHostScreen", currentRoute.toString())
    val currentScreen = NavigationRoutes.fromRoute(currentRoute)
    Log.i("MainHostScreen", currentScreen.toString())
    val doNotShowBottomBar = currentScreen == NavigationRoutes.Splash
    LaunchedEffect(currentRoute) {
        topBarViewModel.updateStateForScreen(currentScreen)
    }
    val topBarState by topBarViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            if (currentScreen != NavigationRoutes.Splash) {
                TopAppBar(
                    title = topBarState.title,
                    leftIcon = topBarState.leftIcon,
                    leftIconDescription = topBarState.leftIconDescription,
                    onLeftClick = topBarState.onLeftClickRoute?.let { { navController.navigate(it) } }
                        ?: { navController.popBackStack() },
                    showBackButton = topBarState.showBackButton,
                    actionIcon = topBarState.actionIcon,
                    actionDescription = topBarState.actionDescription,
                    onAction = topBarState.onActionRoute?.let { { navController.navigate(it) } }
                )
            }
        },
        floatingActionButton = {
            if (currentScreen.showFAB) {
                FabAddButton(
                    onClick = { currentScreen.onFloatingActionRoute?.let { navController.navigate(it) } },
                    contentDescription = currentScreen.floatingActionDescription
                )
            }
        },
        bottomBar = {
            if (currentScreen != NavigationRoutes.Splash) {
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