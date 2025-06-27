package ru.kpfu.itis.ya.domain.useCase.income

import ru.kpfu.itis.ya.domain.model.Transaction
import ru.kpfu.itis.ya.domain.repository.TransactionsRepository
import javax.inject.Inject

/**
 * UseCase для получения доходов за указанный период.
 *
 * Single Responsibility: Отвечает только за предоставление списка доходов за выбранный период для указанного аккаунта.
 */
class GetIncomesUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<Transaction> =
        repository.getIncomes(accountId, startDate, endDate)
}
