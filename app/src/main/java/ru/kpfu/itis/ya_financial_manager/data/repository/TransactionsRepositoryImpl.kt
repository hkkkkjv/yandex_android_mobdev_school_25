package ru.kpfu.itis.ya_financial_manager.data.repository

import ru.kpfu.itis.ya_financial_manager.data.remote.ApiService
import ru.kpfu.itis.ya_financial_manager.domain.mapper.toDomain
import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction
import ru.kpfu.itis.ya_financial_manager.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : TransactionsRepository {
    override suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> {
        return api.getAccountTransactions(accountId, startDate, endDate).map { it.toDomain() }
    }
    override suspend fun getExpenses(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> {
        return getTransactions(accountId, startDate, endDate).filter { !it.category.isIncome }
    }

    override suspend fun getIncomes(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> {
        return getTransactions(accountId, startDate, endDate).filter { it.category.isIncome }
    }
}