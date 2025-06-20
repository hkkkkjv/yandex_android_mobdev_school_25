package ru.kpfu.itis.ya_financial_manager.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("emoji") val emoji: String,
    @SerialName("isIncome") val isIncome: Boolean
)