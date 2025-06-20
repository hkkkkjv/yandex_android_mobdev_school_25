package ru.kpfu.itis.ya_financial_manager.domain.repository

import ru.kpfu.itis.ya_financial_manager.domain.model.Account
import ru.kpfu.itis.ya_financial_manager.domain.model.AccountResponse

interface AccountsRepository {
    suspend fun getAccounts(): List<Account>
    suspend fun getAccountById(id: Int): AccountResponse
}