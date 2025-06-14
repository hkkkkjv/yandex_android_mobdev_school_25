package ru.kpfu.itis.ya_financial_manager.presentation.screens.expenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.model.MockData
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.FabAddButton
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ListItem
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.TopAppBar
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.displayAmountWithCurrency

@Composable
fun ExpensesScreen() {
    val expenses = MockData.expenses
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = stringResource(R.string.expenses_today),
                    rightIconResId = R.drawable.ic_outline_history_24,
                    onRightClick = { /* TODO: обновить */ }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                LazyColumn {
                    item(key = "total") {
                        ListItem(
                            title = stringResource(R.string.total),
                            trailText = displayAmountWithCurrency(
                                MockData.mockTotalExpenses.toString(),
                                MockData.mockTotalCurrency
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
        }
        FabAddButton(
            onClick = { /* TODO: добавить расход */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        )
    }
}