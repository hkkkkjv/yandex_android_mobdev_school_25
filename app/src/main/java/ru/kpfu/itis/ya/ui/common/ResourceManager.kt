package ru.kpfu.itis.ya.ui.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерфейс для работы с ресурсами приложения.
 *
 * Single Responsibility: Отвечает только за предоставление доступа к строковым ресурсам приложения.
 */
interface ResourceManager {
    fun getString(@StringRes id: Int): String
}

/**
 * Реализация менеджера ресурсов приложения.
 *
 * Single Responsibility: Отвечает только за получение строковых ресурсов из контекста приложения.
 */
@Singleton
class ResourceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceManager {
    override fun getString(@StringRes id: Int): String =
        context.getString(id)
}
