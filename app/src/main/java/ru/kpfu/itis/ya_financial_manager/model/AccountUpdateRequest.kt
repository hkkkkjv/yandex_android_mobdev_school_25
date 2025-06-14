package ru.kpfu.itis.ya_financial_manager.model

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)