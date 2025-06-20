package ru.kpfu.itis.ya_financial_manager.presentation.screens.incomes

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
import ru.kpfu.itis.ya_financial_manager.domain.useCase.income.GetIncomesUseCase
import ru.kpfu.itis.ya_financial_manager.presentation.common.BaseViewModel
import ru.kpfu.itis.ya_financial_manager.presentation.common.ResourceManager
import ru.kpfu.itis.ya_financial_manager.presentation.common.runSuspendCatching
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getIncomesUseCase: GetIncomesUseCase,
    private val networkMonitor: NetworkMonitor,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase
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
            if (!networkMonitor.observe().first()) {
                _state.value =
                    IncomesState.Error(resourceManager.getString(R.string.error_no_internet_connection))
                return@launch
            }
            requireAccountId().onSuccess { accountId ->
                val result = runSuspendCatching { getIncomesUseCase(accountId) }
                _state.value = result.fold(
                    onSuccess = { IncomesState.Success(it.toImmutableList()) },
                    onFailure = {
                        IncomesState.Error(
                            it.localizedMessage ?: resourceManager.getString(R.string.error_unknown)
                        )
                    }
                )
            }.onFailure {
                _state.value = IncomesState.Error(
                    it.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
                )
            }
        }
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