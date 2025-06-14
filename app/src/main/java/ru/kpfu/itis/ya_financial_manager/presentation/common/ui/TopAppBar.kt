package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = null,
    @DrawableRes leftIconResId: Int? = null,
    onLeftClick: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    @DrawableRes rightIconResId: Int? = null,
    onRightClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            if ((leftIcon != null || leftIconResId != null) && onLeftClick != null) {
                IconButton(
                    onClick = onLeftClick,
                ) {
                    if (leftIcon != null) {
                        androidx.compose.material3.Icon(
                            imageVector = leftIcon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    } else if (leftIconResId != null) {
                        Image(
                            painter = painterResource(id = leftIconResId),
                            contentDescription = null
                        )
                    }
                }
            }
        },
        actions = {
            if ((rightIcon != null || rightIconResId != null) && onRightClick != null) {
                IconButton(
                    onClick = onRightClick,
                ) {
                    if (rightIcon != null) {
                        androidx.compose.material3.Icon(
                            imageVector = rightIcon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    } else if (rightIconResId != null) {
                        Image(
                            painter = painterResource(id = rightIconResId),
                            contentDescription = null
                        )
                    }
                }
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor
        )
    )
}