package ru.kpfu.itis.ya_financial_manager.domain.model

data class StatItem(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String
)