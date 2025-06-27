package ru.kpfu.itis.ya.ui.common

import kotlinx.coroutines.CancellationException

/**
 * Утилитарная функция для безопасного выполнения suspend-блоков.
 *
 * Single Responsibility: Отвечает только за обертку suspend-блоков в Result с обработкой исключений.
 */
@SuppressWarnings("TooGenericExceptionCaught")
inline fun <T> runSuspendCatching(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
