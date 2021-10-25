package com.alokomkar.repository

import com.alokomkar.core.Result
import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import kotlinx.coroutines.flow.Flow

interface ICurrencyRepository {
    fun fetchCurrencyRates(): Flow<Result<List<CurrencyRate>>>
    fun fetchSupportedCurrencies(): Flow<Result<List<SupportedCurrency>>>
}