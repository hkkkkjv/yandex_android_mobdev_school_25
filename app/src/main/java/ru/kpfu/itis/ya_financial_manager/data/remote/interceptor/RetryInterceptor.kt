package ru.kpfu.itis.ya_financial_manager.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(
    private val maxRetries: Int = 3,
    private val delayMillis: Long = 2000
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var tryCount = 0
        //Можно проверить на 404 если поменять какой то запрос в апи сервисе
        while (response.code in 500..599 && tryCount < maxRetries) {
            response.close()
            tryCount++
            try {
                Thread.sleep(delayMillis)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                throw IOException(e)
            }
            response = chain.proceed(request)
        }
        return response
    }
}