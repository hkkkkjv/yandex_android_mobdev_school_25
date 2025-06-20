package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

fun formatAmount(amount: String): String {
    val isNegative = amount.trim().startsWith("-")
    val absAmount = amount.trim().removePrefix("-")
    val intPart = absAmount.substringBefore('.')
    val formatted = intPart.reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
    return if (isNegative) "-$formatted" else formatted
}