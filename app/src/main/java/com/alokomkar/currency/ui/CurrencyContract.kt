package com.alokomkar.currency.ui

import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency

sealed class CurrencyState

data class UiState(
    val isLoading: Boolean = false,
    val supportedCurrencies: List<SupportedCurrency> = emptyList(),
    val currencyRates: List<CurrencyRate> = emptyList()
): CurrencyState()

data class ErrorState(val error: Throwable? = null): CurrencyState()