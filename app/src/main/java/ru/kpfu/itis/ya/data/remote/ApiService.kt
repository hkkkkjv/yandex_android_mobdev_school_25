package ru.kpfu.itis.ya.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kpfu.itis.ya.data.dto.AccountDto
import ru.kpfu.itis.ya.data.dto.AccountResponseDto
import ru.kpfu.itis.ya.data.dto.TransactionResponseDto
import ru.kpfu.itis.ya.domain.model.AccountUpdateRequest

/**
 * Интерфейс для работы с API сервером.
 *
 * Single Responsibility: Отвечает только за определение эндпоинтов для получения данных с сервера.
 */
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

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body request: AccountUpdateRequest
    ): AccountDto
}
