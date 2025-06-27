package ru.kpfu.itis.ya.domain.mapper

import ru.kpfu.itis.ya.data.dto.TransactionRequestDto
import ru.kpfu.itis.ya.data.dto.TransactionResponseDto
import ru.kpfu.itis.ya.domain.model.Transaction

/**
 * Мапперы для преобразования моделей транзакций между слоями.
 *
 * Single Responsibility: Отвечает только за преобразование данных транзакций между слоями (DTO <-> Domain).
 */
fun TransactionResponseDto.toDomain() = Transaction(
    id = id,
    account = account.toDomain(),
    category = category.toDomain(),
    amount = amount,
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Transaction.toRequestDto() = TransactionRequestDto(
    accountId = account.id,
    categoryId = category.id,
    amount = amount,
    transactionDate = transactionDate,
    comment = comment
)
