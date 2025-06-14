package ru.kpfu.itis.ya_financial_manager.model

data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)