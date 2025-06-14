package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

fun formatAmount(amount: String): String {
    val intPart = amount.substringBefore('.')
    return intPart.reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}