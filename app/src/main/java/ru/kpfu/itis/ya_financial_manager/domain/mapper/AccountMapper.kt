package ru.kpfu.itis.ya_financial_manager.domain.mapper

import ru.kpfu.itis.ya_financial_manager.data.dto.AccountBriefDto
import ru.kpfu.itis.ya_financial_manager.data.dto.AccountDto
import ru.kpfu.itis.ya_financial_manager.data.dto.AccountResponseDto
import ru.kpfu.itis.ya_financial_manager.domain.model.Account
import ru.kpfu.itis.ya_financial_manager.domain.model.AccountBrief
import ru.kpfu.itis.ya_financial_manager.domain.model.AccountResponse

fun AccountBriefDto.toDomain() = AccountBrief(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
)

fun AccountBrief.toDto() = AccountBriefDto(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun AccountDto.toDomain() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
fun AccountResponseDto.toDomain(): AccountResponse {
    return AccountResponse(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        incomeStats = incomeStats.map { it.toDomain() },
        expenseStats = expenseStats.map { it.toDomain() },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}