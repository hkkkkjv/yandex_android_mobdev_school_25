package ru.kpfu.itis.ya_financial_manager.domain.mapper

import ru.kpfu.itis.ya_financial_manager.data.dto.CategoryDto
import ru.kpfu.itis.ya_financial_manager.domain.model.Category

fun CategoryDto.toDomain() = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)

fun Category.toDto() = CategoryDto(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)