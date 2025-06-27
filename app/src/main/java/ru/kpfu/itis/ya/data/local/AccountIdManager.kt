package ru.kpfu.itis.ya.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Менеджер для работы с ID аккаунта в локальном хранилище.
 *
 * Single Responsibility: Отвечает только за сохранение, получение и очистку ID аккаунта в SharedPreferences.
 */
@Singleton
class AccountIdManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_ACCOUNT_ID = "account_id"
    }

    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun saveAccountId(accountId: Int) = withContext(Dispatchers.IO) {
        prefs.edit { putInt(KEY_ACCOUNT_ID, accountId) }
    }

    suspend fun getAccountId(): Int? = withContext(Dispatchers.IO) {
        val id = prefs.getInt(KEY_ACCOUNT_ID, -1)
        if (id != -1) id else null
    }

    suspend fun clearAccountId() = withContext(Dispatchers.IO) {
        prefs.edit { remove(KEY_ACCOUNT_ID) }
    }
}
