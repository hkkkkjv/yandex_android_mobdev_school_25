package ru.kpfu.itis.ya_financial_manager.presentation.screens.expenses

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.data.NetworkMonitor
import ru.kpfu.itis.ya_financial_manager.data.local.AccountIdManager
import ru.kpfu.itis.ya_financial_manager.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya_financial_manager.domain.useCase.expense.GetExpensesUseCase
import ru.kpfu.itis.ya_financial_manager.presentation.common.BaseViewModel
import ru.kpfu.itis.ya_financial_manager.presentation.common.ResourceManager
import ru.kpfu.itis.ya_financial_manager.presentation.common.runSuspendCatching
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val networkMonitor: NetworkMonitor,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase
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
            if (!networkMonitor.observe().first()) {
                _state.value = ExpensesState.Error(resourceManager.getString(R.string.error_no_internet_connection))
                return@launch
            }
            requireAccountId().onSuccess { accountId ->
                val result = runSuspendCatching { getExpensesUseCase(accountId) }
                _state.value = result.fold(
                    onSuccess = { ExpensesState.Success(it.toImmutableList()) },
                    onFailure = {
                        ExpensesState.Error(
                            it.localizedMessage ?: resourceManager.getString(
                                R.string.error_unknown
                            )
                        )
                    }
                )
            }.onFailure {
                _state.value = ExpensesState.Error(
                    it.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
                )
            }
        }
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