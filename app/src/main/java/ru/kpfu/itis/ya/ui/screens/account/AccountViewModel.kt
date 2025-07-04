package ru.kpfu.itis.ya.ui.screens.account

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.ui.common.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.model.AccountResponse
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountByIdUseCase
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.ui.common.BaseViewModel
import ru.kpfu.itis.ya.ui.common.ResourceManager
import ru.kpfu.itis.ya.ui.common.runSuspendCatching
import javax.inject.Inject

/**
 * ViewModel для экрана аккаунта.
 *
 * Single Responsibility: Отвечает только за управление состоянием и бизнес-логикой экрана аккаунта.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase,
    override val networkMonitor: NetworkMonitor,
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
            if (!checkNetworkConnection()) return@launch
            _state.value = AccountState.Loading
            requireAccountId().fold(
                onSuccess = { accountId -> fetchAccountInfo(accountId) },
                onFailure = { handleAccountError(it) }
            )
        }
    }

    private suspend fun checkNetworkConnection(): Boolean {
        if (!isNetworkAvailable()) {
            _state.value =
                AccountState.Error(resourceManager.getString(R.string.error_no_internet_connection))
            return false
        }
        return true
    }

    private suspend fun fetchAccountInfo(accountId: Int) {
        val result = runSuspendCatching { getAccountByIdUseCase(accountId) }
        result.fold(
            onSuccess = { updateAccountSuccess(it) },
            onFailure = { handleFetchError(it) }
        )
    }

    private fun updateAccountSuccess(accountInfo: AccountResponse) {
        _state.value = AccountState.Success(accountInfo)
    }

    private fun handleAccountError(throwable: Throwable) {
        _state.value = AccountState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
        )
    }

    private fun handleFetchError(throwable: Throwable) {
        _state.value = AccountState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_unknown)
        )
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}
