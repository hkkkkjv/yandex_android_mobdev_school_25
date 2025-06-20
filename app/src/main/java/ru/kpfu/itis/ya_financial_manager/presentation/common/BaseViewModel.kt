package ru.kpfu.itis.ya_financial_manager.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya_financial_manager.data.local.AccountIdManager
import ru.kpfu.itis.ya_financial_manager.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya_financial_manager.presentation.navigation.AppNavigator
import ru.kpfu.itis.ya_financial_manager.presentation.navigation.NavigationAction
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    abstract val accountIdManager: AccountIdManager
    abstract val getAccountsUseCase: GetAccountsUseCase

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
            ?: throw IllegalStateException("No accounts found for user")
        accountIdManager.saveAccountId(accountId)
        return accountId
    }
}