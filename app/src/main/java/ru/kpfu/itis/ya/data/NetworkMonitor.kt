package ru.kpfu.itis.ya.data

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для мониторинга состояния сети.
 *
 * Single Responsibility: Отвечает только за предоставление информации о доступности сети.
 */
interface NetworkMonitor {
    fun observe(): Flow<Boolean>
}
