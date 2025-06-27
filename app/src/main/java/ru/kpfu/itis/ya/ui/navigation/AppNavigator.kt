package ru.kpfu.itis.ya.ui.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Навигатор для управления навигацией в приложении.
 *
 * Single Responsibility: Отвечает только за эмиссию навигационных действий через Flow.
 */
@Singleton
class AppNavigator @Inject constructor() {
    private val _navigationAction = MutableSharedFlow<NavigationAction>()
    val navigationAction: Flow<NavigationAction> = _navigationAction.asSharedFlow()

    suspend fun navigate(navigationAction: NavigationAction) {
        _navigationAction.emit(navigationAction)
    }
}
