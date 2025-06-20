package ru.kpfu.itis.ya_financial_manager.domain.mapper

import ru.kpfu.itis.ya_financial_manager.data.dto.TransactionRequestDto
import ru.kpfu.itis.ya_financial_manager.data.dto.TransactionResponseDto
import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction

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