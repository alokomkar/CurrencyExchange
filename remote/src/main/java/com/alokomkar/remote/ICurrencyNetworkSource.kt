package com.alokomkar.remote

import com.alokomkar.remote.parser.CurrencyParser
import com.alokomkar.remote.parser.SupportedCurrenciesParser
import com.alokomkar.core.Result

interface ICurrencyNetworkSource {
    suspend fun getCurrencyQuotes(): Result<CurrencyParser>
    suspend fun getSupportedCurrencies(): Result<SupportedCurrenciesParser>
}