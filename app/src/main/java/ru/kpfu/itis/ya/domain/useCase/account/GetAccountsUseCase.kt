package ru.kpfu.itis.ya.domain.useCase.account

import ru.kpfu.itis.ya.domain.model.Account
import ru.kpfu.itis.ya.domain.repository.AccountsRepository
import javax.inject.Inject

/**
 * UseCase для получения всех аккаунтов пользователя.
 *
 * Single Responsibility: Отвечает только за предоставление списка всех аккаунтов пользователя.
 */
class GetAccountsUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(): List<Account> =
        repository.getAccounts()
}
