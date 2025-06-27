package ru.kpfu.itis.ya.data.repository

import ru.kpfu.itis.ya.data.remote.ApiService
import ru.kpfu.itis.ya.domain.mapper.toDomain
import ru.kpfu.itis.ya.domain.model.Account
import ru.kpfu.itis.ya.domain.model.AccountResponse
import ru.kpfu.itis.ya.domain.repository.AccountsRepository
import javax.inject.Inject

/**
 * Реализация репозитория банковских счетов.
 *
 * Single Responsibility: Отвечает только за получение информации о счетах через ApiService.
 */
class AccountsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AccountsRepository {
    override suspend fun getAccounts(): List<Account> =
        apiService.getAccounts().map { it.toDomain() }

    override suspend fun getAccountById(id: Int): AccountResponse =
        apiService.getAccountById(id).toDomain()
}
