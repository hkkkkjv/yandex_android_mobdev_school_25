package ru.kpfu.itis.ya.ui.screens.account

sealed class AccountEvent {
    data object Refresh : AccountEvent()
}
