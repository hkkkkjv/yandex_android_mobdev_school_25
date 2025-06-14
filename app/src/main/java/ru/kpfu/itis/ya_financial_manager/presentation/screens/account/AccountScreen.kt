package ru.kpfu.itis.ya_financial_manager.presentation.screens.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.model.MockData
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.FabAddButton
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ListItem
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.TopAppBar

@Composable
fun AccountScreen() {
    val account = MockData.account
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = stringResource(R.string.my_account),
                    rightIconResId = R.drawable.ic_outline_edit_24,
                    onRightClick = { /* TODO: обработка редактирования */ }
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
                    item(key = "balance") {
                        ListItem(
                            showIcon = true,
                            emoji = "💰",
                            title = stringResource(R.string.balance),
                            trailText = account.balance,
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
                            trailText = account.currency,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            trailIcon = Icons.Default.ChevronRight,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            itemHeight = 56.dp
                        )
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