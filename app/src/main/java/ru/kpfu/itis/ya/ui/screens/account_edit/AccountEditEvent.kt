package ru.kpfu.itis.ya.ui.screens.account_edit

import ru.kpfu.itis.ya.ui.common.Currency

sealed class AccountEditEvent {
    data class NameChanged(val name: String) : AccountEditEvent()
    data class BalanceChanged(val balance: String) : AccountEditEvent()
    data class CurrencyChanged(val currency: Currency) : AccountEditEvent()
    data object Save : AccountEditEvent()
    data object Cancel : AccountEditEvent()
}
