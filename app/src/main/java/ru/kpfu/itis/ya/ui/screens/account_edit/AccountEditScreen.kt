package ru.kpfu.itis.ya.ui.screens.account_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.ui.common.Currency.Companion.fromSymbol

@Composable
fun AccountEditScreen(
    viewModel: AccountEditViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadAccount()
    }
    val state by viewModel.state.collectAsState()
    when (val s = state) {
        is AccountEditState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AccountEditState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = s.message, color = MaterialTheme.colorScheme.error)
            }
        }

        is AccountEditState.Success -> {
            LaunchedEffect(Unit) { onBack() }
        }

        is AccountEditState.Edit -> {
            AccountEditContent(
                state = s,
                onEvent = viewModel::onEvent,
                onBack = onBack
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountEditContent(
    state: AccountEditState.Edit,
    onEvent: (AccountEditEvent) -> Unit,
    onBack: () -> Unit
) {
    var showCurrencyDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(AccountEditEvent.NameChanged(it)) },
            label = { Text(stringResource(R.string.account_name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isSaving
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.currency.symbol,
            onValueChange = {},
            label = { Text(stringResource(R.string.currency)) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showCurrencyDialog = true },
            enabled = false,
            readOnly = true
        )
        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = state.error, color = MaterialTheme.colorScheme.error)
        }
    }
    if (showCurrencyDialog) {
        CurrencyPickerDialog(
            selected = state.currency.symbol,
            onSelect = {
                onEvent(AccountEditEvent.CurrencyChanged(fromSymbol(it)))
                showCurrencyDialog = false
            },
            onDismiss = { showCurrencyDialog = false }
        )
    }
}

@Composable
private fun CurrencyPickerDialog(
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val currencies = listOf(
        "₽" to "Российский рубль ₽",
        "$" to "Американский доллар $",
        "€" to "Евро €"
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.currency)) },
        text = {
            Column {
                currencies.forEach { (symbol, label) ->
                    ListItem(
                        headlineContent = { Text(label) },
                        modifier = Modifier.clickable { onSelect(symbol) },
                        trailingContent = {
                            if (selected == symbol) Icon(Icons.Default.Check, null)
                        }
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}