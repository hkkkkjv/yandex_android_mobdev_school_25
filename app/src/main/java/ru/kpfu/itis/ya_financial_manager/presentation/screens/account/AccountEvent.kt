package ru.kpfu.itis.ya_financial_manager.presentation.screens.account

sealed class AccountEvent {
    data object Refresh : AccountEvent()
}