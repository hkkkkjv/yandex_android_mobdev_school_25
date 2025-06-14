package ru.kpfu.itis.ya_financial_manager.model

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)