package ru.kpfu.itis.ya.ui.screens.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.domain.model.AccountResponse
import ru.kpfu.itis.ya.ui.common.ui.ErrorDialog
import ru.kpfu.itis.ya.ui.common.ui.ListItem

@Suppress("LongMethod")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesScreen(
    viewModel: ArticlesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading = state is ArticlesState.Loading
    val onEvent = viewModel::onEvent

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { onEvent(ArticlesEvent.Refresh) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        when (val currentState = state) {
            is ArticlesState.Loading -> {}
            is ArticlesState.Error -> {
                ErrorDialog(
                    error = currentState.message,
                    onDismiss = { onEvent(ArticlesEvent.Refresh) }
                )
            }

            is ArticlesState.Success -> {
                InternalArticlesScreen(accountInfo = currentState.accountInfo)
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
private fun InternalArticlesScreen(accountInfo: AccountResponse) {
    val articles = accountInfo.expenseStats.plus(accountInfo.incomeStats)
    var search by remember { mutableStateOf("") }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text(stringResource(R.string.search_category)) },
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        LazyColumn {
            items(articles, key = { it.categoryId }) { article ->
                ListItem(
                    emoji = article.emoji,
                    showIcon = true,
                    title = article.categoryName,
                    iconBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                    itemHeight = 70.dp
                )
                HorizontalDivider()
            }
        }
    }
}
