package ru.kpfu.itis.ya.ui.screens.account_edit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.ui.common.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.model.AccountUpdateRequest
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountByIdUseCase
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.domain.useCase.account.UpdateAccountUseCase
import ru.kpfu.itis.ya.ui.common.BaseViewModel
import ru.kpfu.itis.ya.ui.common.Currency
import ru.kpfu.itis.ya.ui.common.Currency.Companion.currencyString
import ru.kpfu.itis.ya.ui.common.currencySymbol
import javax.inject.Inject

@HiltViewModel
class AccountEditViewModel @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase,
    override val networkMonitor: NetworkMonitor,
) : BaseViewModel() {
    private val _state = MutableStateFlow<AccountEditState>(AccountEditState.Loading)
    val state: StateFlow<AccountEditState> = _state.asStateFlow()

    private var job: Job? = null

    fun loadAccount() {
        job?.cancel()
        _state.value = AccountEditState.Loading
        job = viewModelScope.launch {
            requireAccountId().fold(
                onSuccess = { accountId ->
                    try {
                        val account = getAccountByIdUseCase(accountId)
                        _state.value = AccountEditState.Edit(
                            id = account.id,
                            name = account.name,
                            balance = account.balance,
                            currency = Currency.fromSymbol(currencySymbol( account.currency))
                        )
                    } catch (e: Exception) {
                        _state.value = AccountEditState.Error(e.localizedMessage ?: "Ошибка загрузки счёта")
                    }
                },
                onFailure = { e ->
                    _state.value = AccountEditState.Error(e.localizedMessage ?: "Ошибка получения id счёта")
                }
            )
        }
    }

    fun onEvent(event: AccountEditEvent) {
        val currentState = _state.value
        when (event) {
            is AccountEditEvent.NameChanged -> {
                if (currentState is AccountEditState.Edit) {
                    _state.value = currentState.copy(name = event.name, error = null)
                }
            }
            is AccountEditEvent.BalanceChanged -> {
                if (currentState is AccountEditState.Edit) {
                    _state.value = currentState.copy(name = event.balance, error = null)
                }
            }
            is AccountEditEvent.CurrencyChanged -> {
                if (currentState is AccountEditState.Edit) {
                    _state.value = currentState.copy(currency = event.currency, error = null)
                }
            }
            AccountEditEvent.Save -> {
                if (currentState is AccountEditState.Edit) {
                    saveAccount(currentState)
                }
            }
            AccountEditEvent.Cancel -> {
                job?.cancel()
            }
        }
    }

    private fun saveAccount(state: AccountEditState.Edit) {
        job?.cancel()
        _state.value = state.copy(isSaving = true, error = null)
        job = viewModelScope.launch {
            try {
                updateAccountUseCase(
                    state.id,
                    AccountUpdateRequest(
                        name = state.name,
                        balance = state.balance,
                        currency = currencyString(state.currency)
                    )
                )
                _state.value = AccountEditState.Success
            } catch (e: Exception) {
                _state.value = state.copy(isSaving = false, error = e.localizedMessage ?: "Ошибка сохранения")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
