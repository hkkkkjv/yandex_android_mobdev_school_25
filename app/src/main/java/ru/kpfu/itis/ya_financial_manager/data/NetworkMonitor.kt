package ru.kpfu.itis.ya_financial_manager.data

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun observe(): Flow<Boolean>
}