package ru.kpfu.itis.ya.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.data.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.ui.navigation.AppNavigator
import ru.kpfu.itis.ya.ui.navigation.NavigationAction
import javax.inject.Inject

/**
 * Базовый ViewModel для всех экранов приложения.
 *
 * Single Responsibility: Отвечает только за предоставление
 * общих функций для всех ViewModel (навигация, проверка сети, работа с аккаунтом).
 */
abstract class BaseViewModel : ViewModel() {
    abstract val accountIdManager: AccountIdManager
    abstract val getAccountsUseCase: GetAccountsUseCase
    abstract val networkMonitor: NetworkMonitor

    @Inject
    lateinit var appNavigator: AppNavigator

    protected fun navigate(action: NavigationAction) {
        viewModelScope.launch {
            appNavigator.navigate(action)
        }
    }

    protected suspend fun requireAccountId(): Result<Int> {
        return runSuspendCatching {
            accountIdManager.getAccountId() ?: fetchAndSaveAccountId()
        }
    }

    private suspend fun fetchAndSaveAccountId(): Int {
        val accounts = getAccountsUseCase()
        val accountId = accounts.firstOrNull()?.id
            ?: error("No accounts found for user")
        accountIdManager.saveAccountId(accountId)
        return accountId
    }

    protected suspend fun isNetworkAvailable(): Boolean =
        networkMonitor.observe().first()
}
