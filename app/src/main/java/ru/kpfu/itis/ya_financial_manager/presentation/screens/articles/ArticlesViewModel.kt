package ru.kpfu.itis.ya_financial_manager.presentation.screens.articles

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
class ArticlesViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val networkMonitor: NetworkMonitor,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<ArticlesState>(ArticlesState.Loading)
    val state: StateFlow<ArticlesState> = _state.asStateFlow()

    private var fetchJob: Job? = null

    init {
        loadArticles()
    }

    fun onEvent(event: ArticlesEvent) {
        when (event) {
            ArticlesEvent.Refresh -> loadArticles()
        }
    }

    private fun loadArticles() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (!networkMonitor.observe().first()) {
                _state.value = ArticlesState.Error(resourceManager.getString(R.string.error_no_internet_connection))
                return@launch
            }
            _state.value = ArticlesState.Loading
            requireAccountId().onSuccess { accountId ->
                val result = runSuspendCatching { getAccountByIdUseCase(accountId) }
                _state.value = result.fold(
                    onSuccess = { accountInfo ->
                        ArticlesState.Success(accountInfo)
                    },
                    onFailure = { error ->
                        ArticlesState.Error(error.localizedMessage ?: resourceManager.getString(R.string.error_unknown))
                    }
                )
            }.onFailure {
                _state.value = ArticlesState.Error(
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