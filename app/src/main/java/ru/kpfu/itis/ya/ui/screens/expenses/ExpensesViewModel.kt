package ru.kpfu.itis.ya.ui.screens.expenses

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.data.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.domain.useCase.expense.GetTodayExpensesUseCase
import ru.kpfu.itis.ya.ui.common.BaseViewModel
import ru.kpfu.itis.ya.ui.common.ResourceManager
import ru.kpfu.itis.ya.ui.common.runSuspendCatching
import javax.inject.Inject

/**
 * ViewModel для экрана расходов.
 *
 * Single Responsibility: Отвечает только за управление состоянием и бизнес-логикой экрана расходов.
 */
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getExpensesUseCase: GetTodayExpensesUseCase,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase,
    override val networkMonitor: NetworkMonitor,
) : BaseViewModel() {
    private var fetchJob: Job? = null
    private val _state = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
    val state: StateFlow<ExpensesState> = _state

    init {
        Log.i("ExpensesViewModel", "init")
        loadExpenses()
    }

    private fun loadExpenses() {
        fetchJob?.cancel()
        _state.value = ExpensesState.Loading
        fetchJob = viewModelScope.launch {
            handleExpensesLoading()
        }
    }

    private suspend fun handleExpensesLoading() {
        if (!checkNetworkConnection()) return

        requireAccountId().fold(
            onSuccess = { accountId -> fetchExpenses(accountId) },
            onFailure = { handleAccountError(it) }
        )
    }

    private suspend fun checkNetworkConnection(): Boolean {
        if (!isNetworkAvailable()) {
            _state.value =
                ExpensesState.Error(resourceManager.getString(R.string.error_no_internet_connection))
            return false
        }
        return true
    }

    private suspend fun fetchExpenses(accountId: Int) {
        val result = runSuspendCatching { getExpensesUseCase(accountId) }
        _state.value = result.fold(
            onSuccess = { ExpensesState.Success(it.toImmutableList()) },
            onFailure = { handleFetchError(it) }
        ) as ExpensesState
    }

    private fun handleAccountError(throwable: Throwable) {
        _state.value = ExpensesState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
        )
    }

    private fun handleFetchError(throwable: Throwable) {
        _state.value = ExpensesState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_unknown)
        )
    }

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.Refresh -> loadExpenses()
            is ExpensesEvent.OnExpenseClick -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}
