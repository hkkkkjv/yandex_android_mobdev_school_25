package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    showIcon: Boolean = false,
    emoji: String? = null,
    title: String? = null,
    subtitle: String? = null,
    trailText: String? = null,
    trailIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    iconBackgroundColor: Color = Color.Transparent,
    trailingIconTintColor: Color = MaterialTheme.colorScheme.tertiary,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    itemHeight: Dp = 48.dp
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier.clickable {  }),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showIcon) {
                when {
                    !emoji.isNullOrBlank() -> {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(iconBackgroundColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = emoji,
                                style = MaterialTheme.typography.titleMedium,
                                color = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    !title.isNullOrBlank() -> {//на случай если нам все таки не будут возвращать эмодзи
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(iconBackgroundColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initialsFromTitle(title),
                                style = MaterialTheme.typography.labelLarge,
                                color = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .width(142.dp)
                    .height(40.dp)
                    .padding(end = 16.dp),
                contentAlignment = if (subtitle.isNullOrBlank()) Alignment.CenterStart else Alignment.TopStart
            ) {
                Column {
                    if (!title.isNullOrBlank()) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = contentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (!subtitle.isNullOrBlank()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = contentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (trailingContent != null) {
                trailingContent()
            } else {
                if (!trailText.isNullOrBlank()) {
                    Text(
                        text = trailText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = contentColor,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                if (trailIcon != null) {
                    Icon(
                        imageVector = trailIcon,
                        contentDescription = null,
                        tint = trailingIconTintColor,
                        modifier = Modifier
                            .padding(start = if (trailText != null) 8.dp else 16.dp)
                            .size(24.dp)
                    )
                }
            }
        }
    }
}
