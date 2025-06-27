package ru.kpfu.itis.ya.domain.model

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: String, // CREATION, MODIFICATION
    val previousState: AccountState?,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)
