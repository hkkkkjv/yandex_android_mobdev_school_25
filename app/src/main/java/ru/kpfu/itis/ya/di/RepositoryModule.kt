package ru.kpfu.itis.ya.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ya.data.repository.AccountsRepositoryImpl
import ru.kpfu.itis.ya.data.repository.TransactionsRepositoryImpl
import ru.kpfu.itis.ya.domain.repository.AccountsRepository
import ru.kpfu.itis.ya.domain.repository.TransactionsRepository

/**
 * Dagger Hilt-модуль для биндинга репозиториев.
 *
 * Single Responsibility: Отвечает только за предоставление биндингов репозиториев для DI-контейнера.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindExpensesRepository(
        impl: TransactionsRepositoryImpl
    ): TransactionsRepository

    @Binds
    fun bindAccountsRepository(
        impl: AccountsRepositoryImpl
    ): AccountsRepository
}
