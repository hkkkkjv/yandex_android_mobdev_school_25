package ru.kpfu.itis.ya.ui.common

private const val GROUP_SIZE = 3

/**
 * Функция для форматирования суммы с разделителями тысяч.
 *
 * Single Responsibility: Отвечает только за форматирование числовой строки с разделителями тысяч.
 */
fun formatAmount(amount: String): String {
    val isNegative = amount.trim().startsWith("-")
    val absAmount = amount.trim().removePrefix("-")
    val intPart = absAmount.substringBefore('.')
    val formatted = intPart.reversed()
        .chunked(GROUP_SIZE)
        .joinToString(" ")
        .reversed()
    return if (isNegative) "-$formatted" else formatted
}
