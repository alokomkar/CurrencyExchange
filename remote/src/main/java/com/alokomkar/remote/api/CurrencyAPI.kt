package com.alokomkar.remote.api

import com.alokomkar.remote.parser.CurrencyParser
import com.alokomkar.remote.parser.SupportedCurrenciesParser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("/live")
    suspend fun getCurrencyQuotes(): Response<CurrencyParser>
    @GET("/list")
    suspend fun getSupportedCurrencies(@Query("format") format: Int = 1): Response<SupportedCurrenciesParser>
}