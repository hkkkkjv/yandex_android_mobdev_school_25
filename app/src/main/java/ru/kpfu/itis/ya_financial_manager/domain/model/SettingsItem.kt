package ru.kpfu.itis.ya_financial_manager.domain.model


data class SettingsItem(
    val id: String,
    val title: String,
    val value: String? = null,
    val isSwitch: Boolean = false,
    val switchChecked: Boolean = false,
    val onSwitchChange: ((Boolean) -> Unit)? = null,
    val onClick: (() -> Unit)? = null
)