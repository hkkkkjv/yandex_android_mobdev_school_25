package ru.kpfu.itis.ya_financial_manager.presentation.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface ResourceManager {
    fun getString(@StringRes id: Int): String
}

@Singleton
class ResourceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceManager {
    override fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }
}