package ru.kpfu.itis.ya.domain.useCase.income

import ru.kpfu.itis.ya.domain.model.Transaction
import ru.kpfu.itis.ya.domain.repository.TransactionsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * UseCase для получения доходов только за текущий день.
 *
 * Single Responsibility: Отвечает только за предоставление списка доходов за сегодня для указанного аккаунта.
 */
class GetTodayIncomesUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    suspend operator fun invoke(accountId: Int): List<Transaction> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val todayStr = today.format(formatter)
        return repository.getIncomes(accountId, todayStr, todayStr)
    }
}
