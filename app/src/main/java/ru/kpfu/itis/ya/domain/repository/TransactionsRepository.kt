package ru.kpfu.itis.ya.domain.repository

import ru.kpfu.itis.ya.domain.model.Transaction

/**
 * Репозиторий для работы с транзакциями.
 *
 * Single Responsibility: Определяет контракт получения транзакций, расходов и доходов для аккаунта.
 */
interface TransactionsRepository {
    suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction>

    suspend fun getExpenses(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction>

    suspend fun getIncomes(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction>
}
