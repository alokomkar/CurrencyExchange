package com.alokomkar.repository

import com.alokomkar.core.DispatcherProvider
import com.alokomkar.mapper.ICurrencyMapper
import com.alokomkar.local.ICurrencyPersistenceSource
import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.remote.ICurrencyNetworkSource
import com.alokomkar.core.Result
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@DelicateCoroutinesApi
@Singleton
class CurrencyRepository @Inject constructor(
    private val remoteSource: ICurrencyNetworkSource,
    private val localSource: ICurrencyPersistenceSource,
    private val mapper: ICurrencyMapper,
    private val dispatcherProvider: DispatcherProvider
): ICurrencyRepository {

    private var timer: Timer? = null

    init {
        fetchAndSaveCurrencyRates()
    }

    private suspend fun fetchAndSaveSupportedCurrencies(): List<SupportedCurrency> {
        var localSupportedCurrencies = localSource.getAllSupportedCurrency()
        if(localSupportedCurrencies.isNullOrEmpty()) {
            val remoteSupportedCurrencies = remoteSource.getSupportedCurrencies()
            if(remoteSupportedCurrencies is Result.Success) {
                localSupportedCurrencies = mapper.mapToSupportedCurrency(remoteSupportedCurrencies.data)
            }
            else {
                localSupportedCurrencies = emptyList()
            }
        }
        return localSupportedCurrencies
    }

    private fun fetchAndSaveCurrencyRates() {
        val task = object : TimerTask() {
            override fun run() {
                GlobalScope.launch(dispatcherProvider.io) {
                    val remoteCurrencyQuotes = remoteSource.getCurrencyQuotes()
                    if(remoteCurrencyQuotes is Result.Success) {
                        val localCurrencyRates = mapper.mapToCurrencyRate(remoteCurrencyQuotes.data)
                        val localSupportedCurrencies = fetchAndSaveSupportedCurrencies()
                        localSource.updateAllCurrencyRate(localCurrencyRates)
                        localSource.updateAllSupportedCurrency(localSupportedCurrencies)
                    }
                }
            }
        }
        timer = Timer().apply {
            scheduleAtFixedRate(
                task, 0, TimeUnit.MINUTES.toMillis(30)
            )
        }
    }

    override fun fetchCurrencyRates(): Flow<Result<List<CurrencyRate>>>
        = localSource.getAllCurrencyRateAsFlow()

    override fun fetchSupportedCurrencies(): Flow<Result<List<SupportedCurrency>>>
        = localSource.getAllSupportedCurrencyAsFlow()


}