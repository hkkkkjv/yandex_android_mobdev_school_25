package ru.kpfu.itis.ya_financial_manager.presentation.screens.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ErrorDialog
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ListItem
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.displayAmountWithCurrency

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isRefreshing = state is ExpensesState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.onEvent(ExpensesEvent.Refresh) }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pullRefresh(pullRefreshState),
    ) {
        when (val currentState = state) {
            is ExpensesState.Loading -> {}
            is ExpensesState.Error -> {
                ErrorDialog(
                    error = currentState.message,
                    onDismiss = { viewModel.onEvent(ExpensesEvent.Refresh) }
                )
            }

            is ExpensesState.Success -> {
                InternalExpensesScreen(
                    state = currentState,
                    onEvent = viewModel::onEvent
                )
            }

        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(androidx.compose.ui.Alignment.TopCenter)
        )
    }
}

@Composable
fun InternalExpensesScreen(
    state: ExpensesState.Success,
    onEvent: (ExpensesEvent) -> Unit
) {
    val expenses = state.expenses
    LazyColumn {
        item(key = "total") {
            ListItem(
                title = stringResource(R.string.total),
                trailText = displayAmountWithCurrency(
                    expenses.sumOf { it.amount.toDouble() }.toString(),
                    expenses.firstOrNull()?.account?.currency ?: ""
                ),
                itemHeight = 56.dp,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
            HorizontalDivider()
        }
        items(expenses, key = { it.id }) { expense ->
            ListItem(
                emoji = expense.category.emoji,
                showIcon = true,
                title = expense.category.name,
                subtitle = expense.comment,
                trailText = displayAmountWithCurrency(
                    expense.amount,
                    expense.account.currency
                ),
                trailIcon = Icons.Default.ChevronRight,
                iconBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                itemHeight = 68.dp
            )
            HorizontalDivider()
        }
    }
}