package ru.kpfu.itis.ya_financial_manager.model

object MockData {
    val mockTotalExpenses = 436558
    val mockTotalCurrency= "RUB"
    val mockTotalIncomes = 600000
    val categories = listOf(
        Category(1, "Аренда квартиры", "🏠", false),
        Category(2, "Одежда", "👗", false),
        Category(3, "На собачку", "🐶", false),
        Category(4, "Ремонт квартиры", "", false),
        Category(5, "Продукты", "🍬", false),
        Category(6, "Спортзал", "🤸", false),
        Category(7, "Медицина", "💊", false),
        Category(8, "Зарплата", "💰", true),
        Category(9, "Подработка", "💵", true)
    )
    val account = Account(
        id = 1,
        name = "Основной счет",
        balance = "-670 000",
        currency = "RUB",
        createdAt = "2024-01-01T00:00:00Z",
        updatedAt = "2024-02-01T00:00:00Z"
    )
    val expenses = listOf(
        Expense(1, account, categories[0], "100000", "2024-02-01T10:00:00Z", null, "2024-02-01T10:00:00Z", "2024-02-01T10:00:00Z"),
        Expense(2, account, categories[1], "100000", "2024-02-01T11:00:00Z", null, "2024-02-01T11:00:00Z", "2024-02-01T11:00:00Z"),
        Expense(3, account, categories[2], "100000", "2024-02-01T12:00:00Z", "Джек", "2024-02-01T12:00:00Z", "2024-02-01T12:00:00Z"),
        Expense(4, account, categories[2], "100000", "2024-02-01T13:00:00Z", "Энни", "2024-02-01T13:00:00Z", "2024-02-01T13:00:00Z"),
        Expense(5, account, categories[3], "100000", "2024-02-01T14:00:00Z", null, "2024-02-01T14:00:00Z", "2024-02-01T14:00:00Z"),
        Expense(6, account, categories[4], "100000", "2024-02-01T15:00:00Z", null, "2024-02-01T15:00:00Z", "2024-02-01T15:00:00Z"),
        Expense(7, account, categories[5], "100000", "2024-02-01T16:00:00Z", null, "2024-02-01T16:00:00Z", "2024-02-01T16:00:00Z"),
        Expense(8, account, categories[6], "100000", "2024-02-01T17:00:00Z", null, "2024-02-01T17:00:00Z", "2024-02-01T17:00:00Z")
    )
    val incomes = listOf(
        Income(1, account, categories[7], "500000", "2024-02-01T09:00:00Z", null, "2024-02-01T09:00:00Z", "2024-02-01T09:00:00Z"),
        Income(2, account, categories[8], "100000", "2024-02-01T09:30:00Z", null, "2024-02-01T09:30:00Z", "2024-02-01T09:30:00Z")
    )

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