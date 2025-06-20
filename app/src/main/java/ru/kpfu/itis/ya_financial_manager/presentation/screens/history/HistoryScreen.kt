package ru.kpfu.itis.ya_financial_manager.presentation.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.presentation.common.TransactionType
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ErrorDialog
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ListItem
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.displayAmountWithCurrency
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    transactionType: TransactionType = TransactionType.EXPENSES,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    LaunchedEffect(transactionType) {
        viewModel.onEvent(HistoryEvent.SetTransactionType(transactionType))
    }
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val isRefreshing = state.isLoading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onEvent(HistoryEvent.Refresh) }
    )
    ErrorDialog(error = state.error, onDismiss = { onEvent(HistoryEvent.DismissError) })
    ErrorDialog(error = state.validationError, onDismiss = { onEvent(HistoryEvent.DismissValidationError) })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (state.isLoading && state.transactions.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error != null) {
            Text(
                text = state.error!!,
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.error
            )
        } else {
            InternalHistoryScreen(state = state, onEvent = onEvent)
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    if (state.showStartDatePicker) {
        DatePickerModal(
            initialDate = state.startDate,
            onDateSelected = { dateMillis ->
                dateMillis?.let {
                    val localDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()
                    onEvent(HistoryEvent.SetStartDate(localDate))
                }
            },
            onDismiss = { onEvent(HistoryEvent.ToggleStartDatePicker) }
        )
    }

    if (state.showEndDatePicker) {
        DatePickerModal(
            initialDate = state.endDate,
            onDateSelected = { dateMillis ->
                dateMillis?.let {
                    val localDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()
                    onEvent(HistoryEvent.SetEndDate(localDate))
                }
            },
            onDismiss = { onEvent(HistoryEvent.ToggleEndDatePicker) }
        )
    }
}

@Composable
fun InternalHistoryScreen(state: HistoryState, onEvent: (HistoryEvent) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        HistoryHeader(
            startDate = state.startDate,
            endDate = state.endDate,
            total = state.totalAmount,
            onStartDateClick = { onEvent(HistoryEvent.ToggleStartDatePicker) },
            onEndDateClick = { onEvent(HistoryEvent.ToggleEndDatePicker) }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.transactions, key = { it.id }) { transaction ->
                ListItem(
                    showIcon = true,
                    emoji = transaction.emoji,
                    title = transaction.title,
                    subtitle = transaction.subtitle,
                    trailText = transaction.amount,
                    trailingContent = {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = displayAmountWithCurrency(
                                    transaction.amount,
                                    transaction.currency
                                ),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = transaction.date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 8.dp)
                        )
                    },
                    onClick = {},
                    backgroundColor = Color.Transparent,
                    itemHeight = 70.dp,
                    iconBackgroundColor = MaterialTheme.colorScheme.surface
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun HistoryHeader(
    startDate: LocalDate,
    endDate: LocalDate,
    total: String,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit
) {
    Column {
        ListItem(
            title = stringResource(R.string.start_date),
            trailText = startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
            onClick = onStartDateClick,
            itemHeight = 56.dp,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        HorizontalDivider()
        ListItem(
            title = stringResource(R.string.end_date),
            trailText = endDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
            onClick = onEndDateClick,
            itemHeight = 56.dp,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        HorizontalDivider()
        ListItem(
            title = stringResource(R.string.amount),
            trailText = total,
            itemHeight = 56.dp,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialDate: LocalDate,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                // Основные цвета для выбранной даты
                selectedDayContainerColor = MaterialTheme.colorScheme.primary, // Зеленый фон выбранной даты
                selectedDayContentColor = MaterialTheme.colorScheme.onSurface,          // Белый текст на выбранной дате

                // Цвета для текущей даты (сегодня)
                todayContentColor = MaterialTheme.colorScheme.primary,          // Зеленый цвет для сегодняшней даты
                todayDateBorderColor = MaterialTheme.colorScheme.primary,       // Зеленая граница для сегодняшней даты

                // Дополнительные цвета для согласованности
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                headlineContentColor = MaterialTheme.colorScheme.onSurface,
                navigationContentColor = MaterialTheme.colorScheme.onSurface,     // Зеленые стрелки навигации

                // Цвета для обычных дней
                dayContentColor = MaterialTheme.colorScheme.onSurface,
                disabledDayContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),

                // Цвета для выбора года
                selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                currentYearContentColor = MaterialTheme.colorScheme.primary,

                // Остальные цвета
                weekdayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                dividerColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}
