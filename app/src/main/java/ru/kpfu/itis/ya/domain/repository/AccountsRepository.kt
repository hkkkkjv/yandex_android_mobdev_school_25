package ru.kpfu.itis.ya.domain.repository

import ru.kpfu.itis.ya.domain.model.Account
import ru.kpfu.itis.ya.domain.model.AccountResponse
import ru.kpfu.itis.ya.domain.model.AccountUpdateRequest

/**
 * Репозиторий для работы с банковскими счетами.
 *
 * Single Responsibility: Определяет контракт получения информации о счетах пользователя.
 */
interface AccountsRepository {
    suspend fun getAccounts(): List<Account>
    suspend fun getAccountById(id: Int): AccountResponse
    suspend fun updateAccount(id: Int, request: AccountUpdateRequest): Account
}
