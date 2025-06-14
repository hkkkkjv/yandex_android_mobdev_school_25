package ru.kpfu.itis.ya_financial_manager.presentation.common.ui

fun initialsFromTitle(title: String): String {
    val words = title.trim().split("\\s+".toRegex())
    return when {
        words.size >= 2 -> {
            (words[0].firstOrNull()?.uppercaseChar() ?: "").toString() +
                    (words[1].firstOrNull()?.uppercaseChar() ?: "")
        }
        words.size == 1 && words[0].length >= 2 -> {
            words[0].substring(0, 2).uppercase()
        }
        words.size == 1 && words[0].isNotEmpty() -> {
            words[0].first().uppercaseChar().toString()
        }
        else -> ""
    }
}