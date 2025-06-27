package ru.kpfu.itis.ya.ui.screens.articles

sealed class ArticlesEvent {
    data object Refresh : ArticlesEvent()
}
