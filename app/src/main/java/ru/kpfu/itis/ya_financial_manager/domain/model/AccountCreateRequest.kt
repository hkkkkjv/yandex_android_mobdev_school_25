package ru.kpfu.itis.ya_financial_manager.domain.model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)