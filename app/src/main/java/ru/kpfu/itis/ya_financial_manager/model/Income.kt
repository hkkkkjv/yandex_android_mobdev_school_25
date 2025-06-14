package ru.kpfu.itis.ya_financial_manager.model

data class Income(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String? = null,
    val createdAt: String,
    val updatedAt: String
)