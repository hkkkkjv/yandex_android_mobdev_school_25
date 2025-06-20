package ru.kpfu.itis.ya_financial_manager.domain.repository

import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction

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