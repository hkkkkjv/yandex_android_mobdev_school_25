package ru.kpfu.itis.ya.ui.screens.incomes

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.ui.common.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.domain.useCase.income.GetTodayIncomesUseCase
import ru.kpfu.itis.ya.ui.common.BaseViewModel
import ru.kpfu.itis.ya.ui.common.ResourceManager
import ru.kpfu.itis.ya.ui.common.runSuspendCatching
import javax.inject.Inject

/**
 * ViewModel для экрана доходов.
 *
 * Single Responsibility: Отвечает только за управление состоянием и бизнес-логикой экрана доходов.
 */
@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getIncomesUseCase: GetTodayIncomesUseCase,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase,
    override val networkMonitor: NetworkMonitor,
) : BaseViewModel() {
    private var fetchJob: Job? = null
    private val _state = MutableStateFlow<IncomesState>(IncomesState.Loading)
    val state: StateFlow<IncomesState> = _state

    init {
        loadIncomes()
    }

    private fun loadIncomes() {
        fetchJob?.cancel()
        _state.value = IncomesState.Loading
        fetchJob = viewModelScope.launch {
            if (!isNetworkAvailable()) {
                setNetworkError()
                return@launch
            }
            requireAccountId()
                .onSuccess { fetchIncomes(it) }
                .onFailure { setAccountIdError(it) }
        }
    }

    private fun setNetworkError() {
        _state.value =
            IncomesState.Error(resourceManager.getString(R.string.error_no_internet_connection))
    }

    private suspend fun fetchIncomes(accountId: Int) {
        val result = runSuspendCatching { getIncomesUseCase(accountId) }
        _state.value = result.fold(
            onSuccess = { IncomesState.Success(it.toImmutableList()) },
            onFailure = { setUnknownError(it) }
        )
    }

    private fun setAccountIdError(throwable: Throwable) {
        _state.value = IncomesState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
        )
    }

    private fun setUnknownError(throwable: Throwable): IncomesState.Error {
        return IncomesState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_unknown)
        )
    }

    fun onEvent(event: IncomesEvent) {
        when (event) {
            is IncomesEvent.Refresh -> loadIncomes()
            is IncomesEvent.OnIncomeClick -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}
