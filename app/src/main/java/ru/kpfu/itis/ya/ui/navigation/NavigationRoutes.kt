package ru.kpfu.itis.ya.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.ui.common.TransactionType

/**
 * Sealed класс для определения маршрутов навигации приложения.
 *
 * Single Responsibility: Отвечает только за определение конфигурации маршрутов навигации и их параметров.
 */
@Suppress("LongParameterList")
sealed class NavigationRoutes(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    @StringRes val title: Int,
    val showBackButton: Boolean = false,
    @DrawableRes val leftIcon: Int? = null,
    @StringRes val leftIconDescription: Int? = null,
    val onLeftClickRoute: String? = null,
    @DrawableRes val actionIcon: Int? = null,
    @StringRes val actionDescription: Int? = null,
    val onActionRoute: String? = null,
    val showFAB: Boolean = false,
    val onFloatingActionRoute: String? = null,
    @StringRes val floatingActionDescription: Int? = null
) {
    data object Splash : NavigationRoutes(
        "splash",
        icon = R.drawable.ic_splash,
        label = R.string.app_name,
        title = R.string.app_name
    )

    data object Expenses : NavigationRoutes(
        "expenses",
        icon = R.drawable.ic_expenses_24,
        label = R.string.expenses,
        title = R.string.expenses_today,
        actionIcon = R.drawable.ic_history_24,
        actionDescription = R.string.action_history,
        onActionRoute = "history/${TransactionType.EXPENSES.name}",
        showFAB = true,
        // onFloatingActionRoute = "add_expense",
        floatingActionDescription = R.string.add_expense
    )

    data object Incomes : NavigationRoutes(
        "incomes", icon = R.drawable.ic_incomes_24,
        label = R.string.incomes,
        title = R.string.incomes_today,
        actionIcon = R.drawable.ic_history_24,
        actionDescription = R.string.action_history,
        onActionRoute = "history/${TransactionType.INCOMES.name}",
        showFAB = true,
        // onFloatingActionRoute = "add_income",
        floatingActionDescription = R.string.add_income
    )

    data object Account : NavigationRoutes(
        "account",
        icon = R.drawable.ic_account_24,
        label = R.string.account,
        title = R.string.my_account,
        actionIcon = R.drawable.ic_outline_edit_24,
        actionDescription = R.string.edit,
        onActionRoute = "account_edit",
        showFAB = true,
        // onFloatingActionRoute = "add_account",
        floatingActionDescription = R.string.add_account
    )

    data object Articles : NavigationRoutes(
        "categories",
        icon = R.drawable.ic_articles_24,
        label = R.string.articles,
        title = R.string.my_articles
    )

    data object Settings : NavigationRoutes(
        "settings",
        icon = R.drawable.ic_settings_24,
        label = R.string.settings,
        title = R.string.settings
    )

    data object History : NavigationRoutes(
        route = "history/{transactionType}",
        icon = R.drawable.ic_history_analyze_24,
        actionIcon = R.drawable.ic_history_analyze_24,
        actionDescription = R.string.action_history,
        label = R.string.action_history,
        title = R.string.my_history,
        showBackButton = true
    ) {
        fun createRoute(transactionType: TransactionType) =
            "history/${transactionType.name}"
    }

    data object AccountEdit : NavigationRoutes(
        route = "account_edit",
        icon = R.drawable.ic_outline_edit_24,
        actionIcon = R.drawable.ic_check_24,
        leftIcon = R.drawable.ic_close_24,
        label = R.string.my_articles,
        title = R.string.my_articles,
        showBackButton = true
    )

    companion object {
        val screens = listOf(Expenses, Incomes, Account, Articles, Settings, Splash, History)
        fun fromRoute(route: String?): NavigationRoutes {
            return when (route) {
                Splash.route -> Splash
                Expenses.route -> Expenses
                Incomes.route -> Incomes
                Account.route -> Account
                Articles.route -> Articles
                Settings.route -> Settings
                History.route -> History
                AccountEdit.route -> AccountEdit
                else -> Expenses
            }
        }
    }
}
