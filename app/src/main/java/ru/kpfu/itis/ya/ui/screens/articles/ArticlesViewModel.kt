package ru.kpfu.itis.ya.ui.screens.articles

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.data.NetworkMonitor
import ru.kpfu.itis.ya.data.local.AccountIdManager
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountByIdUseCase
import ru.kpfu.itis.ya.domain.useCase.account.GetAccountsUseCase
import ru.kpfu.itis.ya.ui.common.BaseViewModel
import ru.kpfu.itis.ya.ui.common.ResourceManager
import ru.kpfu.itis.ya.ui.common.runSuspendCatching
import javax.inject.Inject

/**
 * ViewModel для экрана статей.
 *
 * Single Responsibility: Отвечает только за управление состоянием и бизнес-логикой экрана статей.
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    override val accountIdManager: AccountIdManager,
    override val getAccountsUseCase: GetAccountsUseCase,
    override val networkMonitor: NetworkMonitor
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
            handleArticlesLoading()
        }
    }

    private suspend fun handleArticlesLoading() {
        if (!checkNetworkConnection()) return

        _state.value = ArticlesState.Loading
        requireAccountId().fold(
            onSuccess = { accountId -> fetchAccountInfo(accountId) },
            onFailure = { handleAccountError(it) }
        )
    }

    private suspend fun checkNetworkConnection(): Boolean {
        if (!isNetworkAvailable()) {
            _state.value =
                ArticlesState.Error(resourceManager.getString(R.string.error_no_internet_connection))
            return false
        }
        return true
    }

    private suspend fun fetchAccountInfo(accountId: Int) {
        val result = runSuspendCatching { getAccountByIdUseCase(accountId) }
        _state.value = result.fold(
            onSuccess = { ArticlesState.Success(it) },
            onFailure = { handleFetchError(it) }
        ) as ArticlesState
    }

    private fun handleAccountError(throwable: Throwable) {
        _state.value = ArticlesState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_no_account_id)
        )
    }

    private fun handleFetchError(throwable: Throwable) {
        _state.value = ArticlesState.Error(
            throwable.localizedMessage ?: resourceManager.getString(R.string.error_unknown)
        )
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}
