package ru.kpfu.itis.ya_financial_manager.presentation.screens.incomes

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
fun IncomesScreen() {
    val incomes = MockData.incomes
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = stringResource(R.string.incomes_today),
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
                            trailText = displayAmountWithCurrency(MockData.mockTotalIncomes.toString(),MockData.mockTotalCurrency),
                            itemHeight = 56.dp,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                        HorizontalDivider()
                    }
                    items(incomes, key = { it.id }) { income ->
                        ListItem(
                            title = income.category.name,
                            trailText = displayAmountWithCurrency(income.amount,income.account.currency),
                            trailIcon = Icons.Default.ChevronRight,
                            iconBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                            itemHeight = 71.dp//так в фигме
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