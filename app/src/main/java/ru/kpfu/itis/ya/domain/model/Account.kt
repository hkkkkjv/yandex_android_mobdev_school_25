package ru.kpfu.itis.ya.domain.model

data class Account(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)
