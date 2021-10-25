package com.alokomkar.mapper

import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.remote.parser.CurrencyParser
import com.alokomkar.remote.parser.SupportedCurrenciesParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyMapper @Inject constructor(): ICurrencyMapper {

    override fun mapToCurrencyRate(currencyParser: CurrencyParser): List<CurrencyRate>
            = mutableListOf<CurrencyRate>().apply {
        for(entry in currencyParser.quotes) {
            add(CurrencyRate(entry.key, entry.value))
        }
    }


    override fun mapToSupportedCurrency(supportedCurrenciesParser: SupportedCurrenciesParser): List<SupportedCurrency>
            = mutableListOf<SupportedCurrency>().apply {
        for(entry in supportedCurrenciesParser.currencies) {
            add(SupportedCurrency(entry.key, entry.value))
        }
    }

}