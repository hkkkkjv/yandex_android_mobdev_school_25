package ru.kpfu.itis.ya.domain.model

object MockData {
    val settings = listOf(
        SettingsItem("theme", "Темная тема", isSwitch = false),
        SettingsItem("color", "Основной цвет"),
        SettingsItem("sound", "Звуки"),
        SettingsItem("haptics", "Хаптики"),
        SettingsItem("password", "Код пароль"),
        SettingsItem("sync", "Синхронизация"),
        SettingsItem("language", "Язык"),
        SettingsItem("about", "О программе")
    )
}
