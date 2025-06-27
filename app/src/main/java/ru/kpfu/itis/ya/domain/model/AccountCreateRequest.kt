package ru.kpfu.itis.ya.domain.model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
