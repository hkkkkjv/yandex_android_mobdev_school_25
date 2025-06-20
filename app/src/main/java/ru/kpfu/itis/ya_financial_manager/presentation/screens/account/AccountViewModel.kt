package ru.kpfu.itis.ya_financial_manager.presentation.screens.account

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.data.NetworkMonitor
import ru.kpfu.itis.ya_financial_manager.data.local.AccountIdManager
import ru.kpfu.itis.ya_financial_manager.domain.useCase.account.GetAccountByIdUseCase
import ru.kpfu.itis.ya_financial_manager.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya_financial_manager.presentation.common.BaseViewModel
import ru.kpfu.itis.ya_financial_manager.presentation.common.ResourceManager
import ru.kpfu.itis.ya_financial_manager.presentation.common.runSuspendCatching
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val networkMonitor: NetworkMonitor,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
    val state: StateFlow<AccountState> = _state.asStateFlow()

    private var fetchJob: Job? = null

    init {
        loadAccountInfo()
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            AccountEvent.Refresh -> loadAccountInfo()
        }
    }

    private fun loadAccountInfo() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (!networkMonitor.observe().first()) {
                _state.value =
                    AccountState.Error(resourceManager.getString(R.string.error_no_internet_connection))
                return@launch
            }
            _state.value = AccountState.Loading
            requireAccountId().onSuccess { accountId ->
                val result = runSuspendCatching { getAccountByIdUseCase(accountId) }
                _state.value = result.fold(
                    onSuccess = { accountInfo ->
                        AccountState.Success(accountInfo)
                    },
                    onFailure = { error ->
                        AccountState.Error(error.localizedMessage ?: resourceManager.getString(R.string.error_unknown))
                    }
                )
            }.onFailure {
                _state.value = AccountState.Error(
                    it.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}