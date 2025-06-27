package ru.kpfu.itis.ya.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.kpfu.itis.ya.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерцептор для добавления авторизационного токена к HTTP-запросам.
 *
 * Single Responsibility: Отвечает только за добавление Bearer-токена к заголовкам HTTP-запросов.
 */
@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.AUTH_TOKEN
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "$BEARER_PREFIX$token")
            .build()
        return chain.proceed(request)
    }

    private companion object {
        const val TAG = "AuthInterceptor"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
}
