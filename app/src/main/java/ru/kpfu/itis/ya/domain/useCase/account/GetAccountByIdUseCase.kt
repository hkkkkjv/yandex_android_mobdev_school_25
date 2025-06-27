package ru.kpfu.itis.ya.domain.useCase.account

import ru.kpfu.itis.ya.domain.model.AccountResponse
import ru.kpfu.itis.ya.domain.repository.AccountsRepository
import javax.inject.Inject

/**
 * UseCase для получения информации об аккаунте по его id.
 *
 * Single Responsibility: Отвечает только за предоставление информации об аккаунте по id пользователя.
 */
class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(id: Int): AccountResponse =
        repository.getAccountById(id)
}
