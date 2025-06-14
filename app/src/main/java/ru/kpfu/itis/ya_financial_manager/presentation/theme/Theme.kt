package ru.kpfu.itis.ya_financial_manager.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = Primary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    surfaceContainer = SurfaceContainer,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    tertiary = Tertiary,
    error = Error,
    onError = OnPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = Primary,
    background = BackgroundInverse,
    onBackground = OnBackgroundInverse,
    surface = SurfaceInverse,
    onSurface = OnSurfaceInverse,
    error = Error,
    onError = OnPrimary
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val context = LocalContext.current
    LaunchedEffect(darkTheme) {
        val window = (context as Activity).window
        window.statusBarColor = Primary.toArgb()//Работает с API <35
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = !darkTheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}