package ru.kpfu.itis.ya.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.ya.R
import ru.kpfu.itis.ya.domain.model.MockData
import ru.kpfu.itis.ya.ui.common.ui.ListItem

@Suppress("LongMethod")
@Composable
fun SettingsScreen() {
    var isDarkAuto by remember { mutableStateOf(false) }
    val settings = MockData.settings
    Surface(
        modifier = Modifier
            .fillMaxSize(),
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
                        trailIcon = ImageVector.vectorResource(R.drawable.ic_settings_arrow_right_24),
                        onClick = item.onClick,
                        trailingIconTintColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                HorizontalDivider()
            }
        }
    }
}
