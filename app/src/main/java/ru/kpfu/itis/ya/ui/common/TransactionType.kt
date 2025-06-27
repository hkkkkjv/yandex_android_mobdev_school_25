package ru.kpfu.itis.ya.ui.common

/**
 * Enum для определения типов транзакций.
 *
 * Single Responsibility: Отвечает только за определение типов транзакций (расходы/доходы).
 */
enum class TransactionType {
    EXPENSES, INCOMES
}
