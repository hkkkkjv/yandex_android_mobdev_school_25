package ru.kpfu.itis.ya.domain.model

sealed class ScreenState<out T> {
    data object Loading : ScreenState<Nothing>()
    data class Success<T>(val data: T) : ScreenState<T>()
    data class Error(val message: String) : ScreenState<Nothing>()
    data object Empty : ScreenState<Nothing>()
}
