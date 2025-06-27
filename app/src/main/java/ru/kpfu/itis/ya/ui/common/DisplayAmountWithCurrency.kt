package ru.kpfu.itis.ya.ui.common

/**
 * Функция для отображения суммы с валютой.
 *
 * Single Responsibility: Отвечает только за форматирование и отображение суммы с символом валюты.
 */
fun displayAmountWithCurrency(amount: String, currency: String): String =
    "${formatAmount(amount)} ${currencySymbol(currency)}"

/**
 * Функция для получения символа валюты.
 *
 * Single Responsibility: Отвечает только за сопоставление кода валюты с соответствующим символом.
 */
// На будущее учитывая что нам будут отдавать мы будем отображать соответствующие знаки
fun currencySymbol(currency: String): String = when (currency.uppercase()) {
    "RUB" -> "₽"
    "USD" -> "$"
    "EUR" -> "€"
    else -> currency
}
