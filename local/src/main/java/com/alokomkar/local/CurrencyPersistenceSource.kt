package com.alokomkar.local

import com.alokomkar.local.dao.ICurrencyDAO
import com.alokomkar.local.dao.ISupportedCurrencyDAO
import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.core.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyPersistenceSource @Inject constructor(
    private val currencyDAO: ICurrencyDAO,
    private val supportedCurrencyDAO: ISupportedCurrencyDAO
): ICurrencyPersistenceSource {

    override fun getAllCurrencyRateAsFlow(): Flow<Result<List<CurrencyRate>>>
            = currencyDAO.getAllAsFlow().map { currencyRates ->
        return@map if(currencyRates.isNullOrEmpty())
            Result.Error(Exception("No Currency Rates Present"))
        else
            Result.Success(currencyRates)
    }

    override fun getAllSupportedCurrencyAsFlow(): Flow<Result<List<SupportedCurrency>>>
            = supportedCurrencyDAO.getAllAsFlow().map { supportedCurrencies ->
        return@map if(supportedCurrencies.isNullOrEmpty())
            Result.Error(Exception("No Supported Currency Present"))
        else
            Result.Success(supportedCurrencies)
    }

    override fun getAllSupportedCurrency(): List<SupportedCurrency>
        = supportedCurrencyDAO.getAll()

    override fun updateAllCurrencyRate(currencyRates: List<CurrencyRate>)
        = currencyDAO.insertAll(currencyRates)

    override fun updateAllSupportedCurrency(supportedCurrencies: List<SupportedCurrency>)
        = supportedCurrencyDAO.insertAll(supportedCurrencies)

}