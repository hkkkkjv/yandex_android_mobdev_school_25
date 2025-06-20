package ru.kpfu.itis.ya_financial_manager.domain.useCase.expense

import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction
import ru.kpfu.itis.ya_financial_manager.domain.repository.TransactionsRepository
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<Transaction> {
        return repository.getExpenses(
            accountId,
            startDate,
            endDate
        )
    }
}