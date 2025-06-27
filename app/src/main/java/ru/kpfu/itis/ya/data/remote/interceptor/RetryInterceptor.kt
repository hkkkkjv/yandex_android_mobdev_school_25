package ru.kpfu.itis.ya.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val DEFAULT_MAX_RETRIES = 3
private const val DEFAULT_DELAY_MILLIS = 2000L
private const val HTTP_SERVER_ERROR_START = 500
private const val HTTP_SERVER_ERROR_END = 599

/**
 * Интерцептор для повторных попыток HTTP-запросов при серверных ошибках.
 *
 * Single Responsibility: Отвечает только за автоматический повтор HTTP-запросов при получении 5xx ошибок.
 */
class RetryInterceptor(
    private val maxRetries: Int = DEFAULT_MAX_RETRIES,
    private val delayMillis: Long = DEFAULT_DELAY_MILLIS
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var tryCount = 0

        while (shouldRetry(response, tryCount)) {
            response.close()
            tryCount++
            delayWithInterruptCheck()
            response = chain.proceed(request)
        }
        return response
    }

    private fun shouldRetry(response: Response, tryCount: Int): Boolean =
        response.code in HTTP_SERVER_ERROR_START..HTTP_SERVER_ERROR_END && tryCount < maxRetries

    @Throws(IOException::class)
    private fun delayWithInterruptCheck() {
        try {
            Thread.sleep(delayMillis)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw IOException(e)
        }
    }
}
