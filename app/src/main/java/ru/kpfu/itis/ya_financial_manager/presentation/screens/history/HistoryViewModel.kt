package ru.kpfu.itis.ya_financial_manager.presentation.screens.history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.data.NetworkMonitor
import ru.kpfu.itis.ya_financial_manager.data.local.AccountIdManager
import ru.kpfu.itis.ya_financial_manager.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya_financial_manager.domain.useCase.expense.GetExpensesUseCase
import ru.kpfu.itis.ya_financial_manager.domain.useCase.income.GetIncomesUseCase
import ru.kpfu.itis.ya_financial_manager.presentation.common.BaseViewModel
import ru.kpfu.itis.ya_financial_manager.presentation.common.ResourceManager
import ru.kpfu.itis.ya_financial_manager.presentation.common.TransactionType
import ru.kpfu.itis.ya_financial_manager.presentation.common.runSuspendCatching
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.displayAmountWithCurrency
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getIncomesUseCase: GetIncomesUseCase,
    private val networkMonitor: NetworkMonitor,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()
    private var fetchJob: Job? = null

    private val _event = MutableSharedFlow<HistoryEvent>()
    val event: SharedFlow<HistoryEvent> = _event

    init {
        loadHistory()
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.SetTransactionType -> {
                _state.update { it.copy(transactionType = event.type) }
                loadHistory()
            }

            is HistoryEvent.SetStartDate -> {
                val newStartDate = event.date
                val currentEndDate = _state.value.endDate

                if (newStartDate.isAfter(currentEndDate)) {
                    _state.update {
                        it.copy(
                            showStartDatePicker = false,
                            validationError = resourceManager.getString(R.string.error_start_date_after_end)
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            startDate = newStartDate,
                            showStartDatePicker = false,
                            validationError = null
                        )
                    }
                    loadHistory()
                }
            }

            is HistoryEvent.SetEndDate -> {
                val newEndDate = event.date
                val currentStartDate = _state.value.startDate

                if (newEndDate.isBefore(currentStartDate)) {
                    _state.update {
                        it.copy(
                            showEndDatePicker = false,
                            validationError = resourceManager.getString(R.string.error_start_date_after_end)
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            endDate = newEndDate,
                            showEndDatePicker = false,
                            validationError = null
                        )
                    }
                    loadHistory()
                }
            }

            is HistoryEvent.ToggleStartDatePicker -> {
                _state.update { it.copy(showStartDatePicker = !it.showStartDatePicker) }
            }

            is HistoryEvent.ToggleEndDatePicker -> {
                _state.update { it.copy(showEndDatePicker = !it.showEndDatePicker) }
            }

            HistoryEvent.Refresh -> loadHistory()
            HistoryEvent.DismissError -> _state.update { it.copy(error = null) }
            HistoryEvent.ClearStartDate -> {
                _state.update {
                    it.copy(
                        startDate = LocalDate.now().withDayOfMonth(1),
                        showStartDatePicker = false
                    )
                }
                loadHistory()
            }

            HistoryEvent.ClearEndDate -> {
                _state.update {
                    it.copy(
                        endDate = LocalDate.now(),
                        showEndDatePicker = false
                    )
                }
                loadHistory()
            }
            HistoryEvent.DismissValidationError -> _state.update { it.copy(validationError = null) }
        }
    }

    private fun loadHistory() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (!networkMonitor.observe().first()) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = resourceManager.getString(R.string.error_no_internet_connection)
                    )
                }
                return@launch
            }
            _state.update { it.copy(isLoading = true, error = null) }
            requireAccountId().onSuccess { accountId ->
                val currentState = _state.value
                val startDateStr = currentState.startDate.toString()
                val endDateStr = currentState.endDate.toString()
                val result = when (currentState.transactionType) {
                    TransactionType.EXPENSES -> runSuspendCatching {
                        getExpensesUseCase(
                            accountId,
                            startDateStr,
                            endDateStr
                        )
                    }

                    TransactionType.INCOMES -> runSuspendCatching {
                        getIncomesUseCase(
                            accountId,
                            startDateStr,
                            endDateStr
                        )
                    }
                }
                result.onSuccess { transactions ->
                    val filteredTransactions = transactions
                        .map { it.toUiModel() }
                        .filter { it.date.toLocalDate() in currentState.startDate..currentState.endDate }
                        .sortedByDescending { it.date }
                    val total = filteredTransactions.sumOf { it.amountValue }
                    _state.update { current ->
                        current.copy(
                            isLoading = false,
                            transactions = filteredTransactions.toImmutableList(),
                            totalAmount = displayAmountWithCurrency(
                                total.toString(),
                                transactions.firstOrNull()?.account?.currency ?: "₽"
                            )
                        )
                    }
                }.onFailure {
                    _state.update { current ->
                        current.copy(
                            isLoading = false,
                            transactions = listOf<TransactionUiModel>().toImmutableList(),
                            error = resourceManager.getString(R.string.error_loading_failed)
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                                ?: resourceManager.getString(R.string.error_no_account_id)
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}
