package ru.kpfu.itis.ya.ui.screens.history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.data.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.model.Transaction
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.domain.useCase.expense.GetExpensesUseCase
import ru.kpfu.itis.ya.domain.useCase.income.GetIncomesUseCase
import ru.kpfu.itis.ya.ui.common.BaseViewModel
import ru.kpfu.itis.ya.ui.common.ResourceManager
import ru.kpfu.itis.ya.ui.common.TransactionType
import ru.kpfu.itis.ya.ui.common.displayAmountWithCurrency
import ru.kpfu.itis.ya.ui.common.runSuspendCatching
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel для экрана истории транзакций.
 *
 * Single Responsibility: Отвечает только за управление состоянием и бизнес-логикой экрана истории транзакций.
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getIncomesUseCase: GetIncomesUseCase,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase,
    override val networkMonitor: NetworkMonitor
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
            is HistoryEvent.SetTransactionType -> handleSetTransactionType(event.type)
            is HistoryEvent.SetStartDate -> handleSetStartDate(event.date)
            is HistoryEvent.SetEndDate -> handleSetEndDate(event.date)
            is HistoryEvent.ToggleStartDatePicker -> handleToggleStartDatePicker()
            is HistoryEvent.ToggleEndDatePicker -> handleToggleEndDatePicker()
            HistoryEvent.Refresh -> handleRefresh()
            HistoryEvent.DismissError -> handleDismissError()
            HistoryEvent.ClearStartDate -> handleClearStartDate()
            HistoryEvent.ClearEndDate -> handleClearEndDate()
            HistoryEvent.DismissValidationError -> handleDismissValidationError()
        }
    }

    private fun handleSetTransactionType(type: TransactionType) {
        _state.update { it.copy(transactionType = type) }
        loadHistory()
    }

    private fun handleSetStartDate(date: LocalDate) {
        val currentEndDate = _state.value.endDate
        if (date.isAfter(currentEndDate)) {
            showValidationError(resourceManager.getString(R.string.error_start_date_after_end))
        } else {
            updateStartDate(date)
            loadHistory()
        }
    }

    private fun handleSetEndDate(date: LocalDate) {
        val currentStartDate = _state.value.startDate
        if (date.isBefore(currentStartDate)) {
            showValidationError(resourceManager.getString(R.string.error_start_date_after_end))
        } else {
            updateEndDate(date)
            loadHistory()
        }
    }

    private fun handleToggleStartDatePicker() {
        _state.update { it.copy(showStartDatePicker = !it.showStartDatePicker) }
    }

    private fun handleToggleEndDatePicker() {
        _state.update { it.copy(showEndDatePicker = !it.showEndDatePicker) }
    }

    private fun handleRefresh() {
        loadHistory()
    }

    private fun handleDismissError() {
        _state.update { it.copy(error = null) }
    }

    private fun handleClearStartDate() {
        _state.update {
            it.copy(
                startDate = LocalDate.now().withDayOfMonth(1),
                showStartDatePicker = false
            )
        }
        loadHistory()
    }

    private fun handleClearEndDate() {
        _state.update {
            it.copy(
                endDate = LocalDate.now(),
                showEndDatePicker = false
            )
        }
        loadHistory()
    }

    private fun handleDismissValidationError() {
        _state.update { it.copy(validationError = null) }
    }

    private fun showValidationError(message: String) {
        _state.update {
            it.copy(
                showStartDatePicker = false,
                showEndDatePicker = false,
                validationError = message
            )
        }
    }

    private fun updateStartDate(date: LocalDate) {
        _state.update {
            it.copy(
                startDate = date,
                showStartDatePicker = false,
                validationError = null
            )
        }
    }

    private fun updateEndDate(date: LocalDate) {
        _state.update {
            it.copy(
                endDate = date,
                showEndDatePicker = false,
                validationError = null
            )
        }
    }

    private fun loadHistory() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (!checkNetworkConnection()) return@launch
            _state.update { it.copy(isLoading = true, error = null) }
            requireAccountId().fold(
                onSuccess = { accountId -> fetchHistoryData(accountId) },
                onFailure = { handleAccountError(it) }
            )
        }
    }

    private suspend fun checkNetworkConnection(): Boolean {
        if (!isNetworkAvailable()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = resourceManager.getString(R.string.error_no_internet_connection)
                )
            }
            return false
        }
        return true
    }

    private suspend fun fetchHistoryData(accountId: Int) {
        val currentState = _state.value
        val startDateStr = currentState.startDate.toString()
        val endDateStr = currentState.endDate.toString()

        val result = when (currentState.transactionType) {
            TransactionType.EXPENSES -> runSuspendCatching {
                getExpensesUseCase(accountId, startDateStr, endDateStr)
            }

            TransactionType.INCOMES -> runSuspendCatching {
                getIncomesUseCase(accountId, startDateStr, endDateStr)
            }
        }

        processHistoryResult(result)
    }

    private fun processHistoryResult(result: Result<List<Transaction>>) {
        result.fold(
            onSuccess = { transactions -> updateHistorySuccess(transactions) },
            onFailure = { handleFetchError(it) }
        )
    }

    private fun updateHistorySuccess(transactions: List<Transaction>) {
        val currentState = _state.value
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
    }

    private fun handleAccountError(throwable: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                error = throwable.message ?: resourceManager.getString(R.string.error_no_account_id)
            )
        }
    }

    private fun handleFetchError(throwable: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                error = throwable.message
                    ?: resourceManager.getString(R.string.error_loading_failed)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}
