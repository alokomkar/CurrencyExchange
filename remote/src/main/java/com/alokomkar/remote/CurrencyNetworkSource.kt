package com.alokomkar.remote

import com.alokomkar.core.DispatcherProvider
import com.alokomkar.core.Result
import com.alokomkar.core.toUpdateResponse
import com.alokomkar.remote.api.CurrencyAPI
import com.alokomkar.remote.parser.CurrencyParser
import com.alokomkar.remote.parser.SupportedCurrenciesParser
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyNetworkSource @Inject constructor(
    private val api: CurrencyAPI,
    private val dispatcherProvider: DispatcherProvider
    ): ICurrencyNetworkSource {

    override suspend fun getCurrencyQuotes(): Result<CurrencyParser> = withContext(dispatcherProvider.io) {
        api.getCurrencyQuotes().toUpdateResponse {
            "Unable to retrieve Currency Quotes"
        }
    }

    override suspend fun getSupportedCurrencies(): Result<SupportedCurrenciesParser> = withContext(dispatcherProvider.io) {
        api.getSupportedCurrencies().toUpdateResponse {
            "Unable to retrieve Supported Currencies"
        }
    }
}