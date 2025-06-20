package ru.kpfu.itis.ya_financial_manager.presentation.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.presentation.navigation.NavigationRoutes

class TopBarViewModel : ViewModel() {
    private val _state = MutableStateFlow(TopBarState(R.string.expenses_today))
    val state: StateFlow<TopBarState> = _state

    fun updateStateForScreen(screen: NavigationRoutes) {
        _state.value = TopBarState(
            title = screen.title,
            leftIcon = screen.leftIcon,
            leftIconDescription = screen.leftIconDescription,
            onLeftClickRoute = screen.onLeftClickRoute,
            showBackButton = screen.showBackButton,
            actionIcon = screen.actionIcon,
            actionDescription = screen.actionDescription,
            onActionRoute = screen.onActionRoute
        )
    }
}

data class TopBarState(
    @StringRes val title: Int,
    @DrawableRes val leftIcon: Int? = null,
    @StringRes val leftIconDescription: Int? = null,
    val onLeftClickRoute: String? = null,
    val showBackButton: Boolean = false,
    @DrawableRes val actionIcon: Int? = null,
    @StringRes val actionDescription: Int? = null,
    val onActionRoute: String? = null
)