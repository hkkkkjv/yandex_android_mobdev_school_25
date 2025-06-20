package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.kpfu.itis.ya_financial_manager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes leftIcon: Int? = null,
    @StringRes leftIconDescription: Int? = null,
    showBackButton: Boolean = false,
    onLeftClick: (() -> Unit)? = null,
    @DrawableRes actionIcon: Int? = null,
    @StringRes actionDescription: Int? = null,
    onAction: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            when {
                leftIcon != null && onLeftClick != null -> {
                    IconButton(onClick = onLeftClick) {
                        Icon(
                            painter = painterResource(id = leftIcon),
                            contentDescription = leftIconDescription?.let { stringResource(it) }
                        )
                    }
                }
                showBackButton && onLeftClick != null -> {
                    IconButton(onClick = onLeftClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            }
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = onAction?:{}) {
                    Icon(
                        painter = painterResource(id = actionIcon),
                        contentDescription = actionDescription?.let { stringResource(it) }
                    )
                }
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor
        )
    )
}