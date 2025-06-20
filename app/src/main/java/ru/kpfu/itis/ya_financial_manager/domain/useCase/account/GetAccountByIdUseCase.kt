package ru.kpfu.itis.ya_financial_manager.domain.useCase.account

import ru.kpfu.itis.ya_financial_manager.domain.model.AccountResponse
import ru.kpfu.itis.ya_financial_manager.domain.repository.AccountsRepository
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(id: Int): AccountResponse {
        return repository.getAccountById(id)
    }
}