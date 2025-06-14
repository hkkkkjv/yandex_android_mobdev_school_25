package ru.kpfu.itis.ya_financial_manager.presentation.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.ya_financial_manager.R
import ru.kpfu.itis.ya_financial_manager.model.MockData
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.ListItem
import ru.kpfu.itis.ya_financial_manager.presentation.common.ui.TopAppBar

@Composable
fun SettingsScreen() {
    var isDarkAuto by remember { mutableStateOf(false) }
    val settings = MockData.settings
    Scaffold(
        topBar = {
            TopAppBar(title = stringResource(R.string.settings))
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn {
                items(settings, key = { it.id }) { item ->
                    if (item.id == "theme") {
                        ListItem(
                            title = item.title,
                            trailingContent = {
                                Switch(
                                    checked = isDarkAuto,
                                    onCheckedChange = { isDarkAuto = it },
                                    modifier = Modifier
                                        .padding(end = 0.dp)
                                        .height(16.dp)
                                )
                            }
                        )
                    } else {
                        ListItem(
                            title = item.title,
                            trailIconResId = R.drawable.ic_settings_arrow_right_24,
                            onClick = item.onClick,
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}