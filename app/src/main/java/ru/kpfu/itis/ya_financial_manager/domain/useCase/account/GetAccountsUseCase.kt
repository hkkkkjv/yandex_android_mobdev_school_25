package ru.kpfu.itis.ya_financial_manager.domain.useCase.account

import ru.kpfu.itis.ya_financial_manager.domain.model.Account
import ru.kpfu.itis.ya_financial_manager.domain.repository.AccountsRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountsRepository
) {
    suspend operator fun invoke(): List<Account> {
        return repository.getAccounts()
    }
}