package ru.kpfu.itis.ya_financial_manager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.ya_financial_manager.navigation.AppNavigator
import ru.kpfu.itis.ya_financial_manager.navigation.MainHostScreen
import ru.kpfu.itis.ya_financial_manager.presentation.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                MainHostScreen(
                    navController = navController,
                    appNavigator = appNavigator
                )
            }
        }
    }
}