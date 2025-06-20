package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.ya_financial_manager.R

@Composable
fun FabAddButton(
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int?,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_add_24),
            contentDescription = contentDescription?.let { stringResource(it) },
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}