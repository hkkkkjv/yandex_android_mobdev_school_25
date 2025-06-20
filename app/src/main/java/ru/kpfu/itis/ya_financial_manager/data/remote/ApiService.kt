package ru.kpfu.itis.ya_financial_manager.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kpfu.itis.ya_financial_manager.data.dto.AccountDto
import ru.kpfu.itis.ya_financial_manager.data.dto.AccountResponseDto
import ru.kpfu.itis.ya_financial_manager.data.dto.TransactionResponseDto

interface ApiService {
    @GET("transactions/account/{accountId}/period")
    suspend fun getAccountTransactions(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): List<TransactionResponseDto>
    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>
    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountResponseDto
}