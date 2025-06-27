package ru.kpfu.itis.ya.domain.mapper

import ru.kpfu.itis.ya.data.dto.StatItemDto
import ru.kpfu.itis.ya.domain.model.StatItem

/**
 * Мапперы для преобразования моделей статистики между слоями.
 *
 * Single Responsibility: Отвечает только за преобразование данных статистики между слоями (DTO <-> Domain).
 */
fun StatItemDto.toDomain(): StatItem {
    return StatItem(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount
    )
}
