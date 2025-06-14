package ru.kpfu.itis.ya_financial_manager.model

data class Account(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)
