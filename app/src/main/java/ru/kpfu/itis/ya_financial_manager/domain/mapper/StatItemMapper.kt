package ru.kpfu.itis.ya_financial_manager.domain.mapper

import ru.kpfu.itis.ya_financial_manager.data.dto.StatItemDto
import ru.kpfu.itis.ya_financial_manager.domain.model.StatItem

fun StatItemDto.toDomain(): StatItem {
    return StatItem(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount
    )
}