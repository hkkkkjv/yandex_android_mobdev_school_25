package ru.kpfu.itis.ya_financial_manager.domain.useCase.income

import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction
import ru.kpfu.itis.ya_financial_manager.domain.repository.TransactionsRepository
import javax.inject.Inject

class GetIncomesUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<Transaction> {
        return repository.getIncomes(accountId, startDate, endDate)
    }
}