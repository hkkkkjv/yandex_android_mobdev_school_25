package ru.kpfu.itis.ya.ui.screens.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.domain.model.AccountResponse
import ru.kpfu.itis.ya.ui.common.currencySymbol
import ru.kpfu.itis.ya.ui.common.displayAmountWithCurrency
import ru.kpfu.itis.ya.ui.common.ui.ErrorDialog
import ru.kpfu.itis.ya.ui.common.ui.ListItem

@Suppress("LongMethod")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading = state is AccountState.Loading
    val onEvent = viewModel::onEvent

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { onEvent(AccountEvent.Refresh) }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        when (val currentState = state) {
            is AccountState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AccountState.Error -> {
                ErrorDialog(
                    error = currentState.message,
                    onDismiss = { onEvent(AccountEvent.Refresh) }
                )
            }

            is AccountState.Success -> {
                InternalAccountScreen(account = currentState.accountInfo)
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Suppress("LongMethod")
@Composable
fun InternalAccountScreen(account: AccountResponse) {
    LazyColumn {
        item(key = "balance") {
            ListItem(
                showIcon = true,
                emoji = "💰",
                title = stringResource(R.string.balance),
                trailText = displayAmountWithCurrency(account.balance, account.currency),
                contentColor = MaterialTheme.colorScheme.onSurface,
                iconBackgroundColor = Color.White,
                trailIcon = Icons.Default.ChevronRight,
                backgroundColor = MaterialTheme.colorScheme.surface,
                itemHeight = 56.dp
            )
            HorizontalDivider()
        }
        item(key = "currency") {
            ListItem(
                title = stringResource(R.string.currency),
                trailText = currencySymbol(account.currency),
                contentColor = MaterialTheme.colorScheme.onSurface,
                trailIcon = Icons.Default.ChevronRight,
                backgroundColor = MaterialTheme.colorScheme.surface,
                itemHeight = 56.dp
            )
        }
    }
}
