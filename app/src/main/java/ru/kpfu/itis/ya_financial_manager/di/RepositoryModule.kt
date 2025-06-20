package ru.kpfu.itis.ya_financial_manager.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.ya_financial_manager.data.repository.AccountsRepositoryImpl
import ru.kpfu.itis.ya_financial_manager.data.repository.TransactionsRepositoryImpl
import ru.kpfu.itis.ya_financial_manager.domain.repository.AccountsRepository
import ru.kpfu.itis.ya_financial_manager.domain.repository.TransactionsRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    abstract fun bindExpensesRepository(
        impl: TransactionsRepositoryImpl
    ): TransactionsRepository

    @Binds
    abstract fun bindAccountsRepository(
        impl: AccountsRepositoryImpl
    ): AccountsRepository
}