package ru.kpfu.itis.ya.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ya.ui.common.NetworkMonitor
import ru.kpfu.itis.ya.data.repository.NetworkMonitorImpl
import ru.kpfu.itis.ya.ui.common.ResourceManager
import ru.kpfu.itis.ya.ui.common.ResourceManagerImpl
import javax.inject.Singleton

/**
 * Dagger Hilt-модуль для биндинга общих зависимостей приложения.
 *
 * Single Responsibility: Отвечает только за предоставление биндингов
 * общих зависимостей (NetworkMonitor, ResourceManager) для DI-контейнера.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        networkMonitor: NetworkMonitorImpl
    ): NetworkMonitor

    @Binds
    @Singleton
    abstract fun bindResourceManager(
        resourceManager: ResourceManagerImpl
    ): ResourceManager
}
