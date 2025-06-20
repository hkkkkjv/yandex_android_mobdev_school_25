package ru.kpfu.itis.ya_financial_manager.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ya_financial_manager.data.NetworkMonitor
import ru.kpfu.itis.ya_financial_manager.presentation.common.NetworkMonitorImpl
import ru.kpfu.itis.ya_financial_manager.presentation.common.ResourceManager
import ru.kpfu.itis.ya_financial_manager.presentation.common.ResourceManagerImpl
import javax.inject.Singleton

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