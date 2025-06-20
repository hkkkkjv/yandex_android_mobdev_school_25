package ru.kpfu.itis.ya_financial_manager.data.repository

import ru.kpfu.itis.ya_financial_manager.data.remote.ApiService
import ru.kpfu.itis.ya_financial_manager.domain.mapper.toDomain
import ru.kpfu.itis.ya_financial_manager.domain.model.Account
import ru.kpfu.itis.ya_financial_manager.domain.model.AccountResponse
import ru.kpfu.itis.ya_financial_manager.domain.repository.AccountsRepository
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AccountsRepository {
    override suspend fun getAccounts(): List<Account> {
        return apiService.getAccounts().map { it.toDomain() }
    }
    override suspend fun getAccountById(id: Int): AccountResponse {
        return apiService.getAccountById(id).toDomain()
    }
}