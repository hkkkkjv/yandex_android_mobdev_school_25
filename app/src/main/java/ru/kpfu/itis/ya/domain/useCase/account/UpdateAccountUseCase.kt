package ru.kpfu.itis.ya.domain.useCase.account

import ru.kpfu.itis.ya.domain.model.Account
import ru.kpfu.itis.ya.domain.model.AccountUpdateRequest
import ru.kpfu.itis.ya.domain.repository.AccountsRepository
import javax.inject.Inject

/**
 * UseCase для обновления информации о счете.
 * Single Responsibility: Отвечает только за обновление счета через репозиторий.
 */
class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(id: Int, request: AccountUpdateRequest): Account =
        repository.updateAccount(id, request)
}
