package ru.kpfu.itis.ya_financial_manager.presentation.screens.articles

sealed class ArticlesEvent {
    data object Refresh : ArticlesEvent()
}