package ru.kpfu.itis.ya.data.repository

import ru.kpfu.itis.ya.data.remote.ApiService
import ru.kpfu.itis.ya.domain.mapper.toDomain
import ru.kpfu.itis.ya.domain.model.Transaction
import ru.kpfu.itis.ya.domain.repository.TransactionsRepository
import javax.inject.Inject

/**
 * Реализация репозитория транзакций.
 *
 * Single Responsibility: Отвечает только за получение и фильтрацию транзакций через ApiService.
 */
class TransactionsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : TransactionsRepository {
    override suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> =
        api.getAccountTransactions(accountId, startDate, endDate).map { it.toDomain() }

    override suspend fun getExpenses(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> =
        getTransactions(accountId, startDate, endDate).filter { !it.category.isIncome }

    override suspend fun getIncomes(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> =
        getTransactions(accountId, startDate, endDate).filter { it.category.isIncome }
}
