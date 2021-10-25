package com.alokomkar.local

import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.core.Result
import kotlinx.coroutines.flow.Flow

interface ICurrencyPersistenceSource {
    fun getAllCurrencyRateAsFlow(): Flow<Result<List<CurrencyRate>>>
    fun getAllSupportedCurrencyAsFlow(): Flow<Result<List<SupportedCurrency>>>
    fun getAllSupportedCurrency(): List<SupportedCurrency>
    fun updateAllCurrencyRate(currencyRates: List<CurrencyRate>)
    fun updateAllSupportedCurrency(supportedCurrencies: List<SupportedCurrency>)
}