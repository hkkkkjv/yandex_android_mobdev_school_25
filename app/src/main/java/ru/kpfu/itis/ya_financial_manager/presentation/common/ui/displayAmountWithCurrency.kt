package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

fun displayAmountWithCurrency(amount: String, currency: String): String {
    return "${formatAmount(amount)} ${currencySymbol(currency)}"
}
//На будущее учитывая что нам будут отдавать мы будем отображать соответствующие знаки
fun currencySymbol(currency: String): String = when (currency.uppercase()) {
    "RUB" -> "₽"
    "USD" -> "$"
    "EUR" -> "€"
    else -> currency
}