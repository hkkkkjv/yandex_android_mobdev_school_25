package ru.kpfu.itis.ya.ui.common

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для мониторинга состояния сети.
 *
 * Single Responsibility: Отвечает только за предоставление информации о доступности сети.
 */
interface NetworkMonitor {
    fun observe(): Flow<Boolean>
}
