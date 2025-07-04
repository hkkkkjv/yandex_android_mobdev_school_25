package ru.kpfu.itis.ya.ui.common

import androidx.annotation.StringRes
import ru.kpfu.itis.ya.R

enum class Currency(
    val symbol: String,
    @StringRes val displayNameRes: Int
) {
    RUB("₽", R.string.currency_rub),
    USD("$", R.string.currency_usd),
    EUR("€", R.string.currency_eur);

    companion object {
        fun fromSymbol(symbol: String): Currency {
            return entries.find { it.symbol == symbol } ?: RUB
        }

        fun currencySymbol(currency: String): String = when (currency.uppercase()) {
            "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            else -> currency
        }

        fun currencyString(currency: Currency): String = when (currency) {
            RUB -> "RUB"
            USD -> "USD"
            EUR -> "EUR"
        }
    }
}