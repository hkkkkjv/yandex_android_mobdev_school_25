package ru.kpfu.itis.ya_financial_manager.presentation.screens.history

import ru.kpfu.itis.ya_financial_manager.domain.model.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TransactionUiModel(
    val id: Int,
    val title: String,
    val subtitle: String?,
    val emoji: String,
    val currency: String,
    val amount: String,
    val amountValue: Double,
    val time: String,
    val date: LocalDateTime,
    val isIncome: Boolean
)

fun Transaction.toUiModel(): TransactionUiModel = TransactionUiModel(
    id = this.id,
    title = this.category.name,
    subtitle = this.comment,
    emoji = this.category.emoji,
    currency = this.account.currency,
    amount = this.amount ,
    amountValue = this.amount.toDoubleOrNull() ?: 0.0,
    time = this.transactionDate,
    date = LocalDateTime.parse(this.transactionDate, DateTimeFormatter.ISO_DATE_TIME),
    isIncome = false
)