package ru.kpfu.itis.ya.domain.useCase.expense

import ru.kpfu.itis.ya.domain.model.Transaction
import ru.kpfu.itis.ya.domain.repository.TransactionsRepository
import javax.inject.Inject

/**
 * UseCase для получения расходов за указанный период.
 *
 * Single Responsibility: Отвечает только за предоставление списка расходов за выбранный период для указанного аккаунта.
 */
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
