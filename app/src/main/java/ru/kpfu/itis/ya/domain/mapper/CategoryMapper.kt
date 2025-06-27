package ru.kpfu.itis.ya.domain.mapper

import ru.kpfu.itis.ya.data.dto.CategoryDto
import ru.kpfu.itis.ya.domain.model.Category

/**
 * Мапперы для преобразования моделей категорий между слоями.
 *
 * Single Responsibility: Отвечает только за преобразование данных категорий между слоями (DTO <-> Domain).
 */
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
