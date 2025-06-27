package ru.kpfu.itis.ya.ui.common.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.kpfu.itis.ya.R

/**
 * Composable-функция для отображения диалога ошибки.
 *
 * Single Responsibility: Отвечает только за отображение диалога с сообщением об ошибке и кнопкой подтверждения.
 */
@Suppress("LongMethod")
@Composable
fun ErrorDialog(
    error: String?,
    onDismiss: () -> Unit
) {
    if (error != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.error)) },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.ok))
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        )
    }
}
