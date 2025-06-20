package ru.kpfu.itis.ya_financial_manager.data.dto

import kotlinx.serialization.SerialName

data class TransactionRequestDto(
    @SerialName("accountId") val accountId: Int,
    @SerialName("categoryId") val categoryId: Int,
    @SerialName("amount") val amount: String,
    @SerialName("transactionDate") val transactionDate: String,
    @SerialName("comment") val comment: String? = null,
)