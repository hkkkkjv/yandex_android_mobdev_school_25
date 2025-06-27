package ru.kpfu.itis.ya.domain.model

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
