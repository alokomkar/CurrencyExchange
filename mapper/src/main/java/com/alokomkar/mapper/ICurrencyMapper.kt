package com.alokomkar.mapper

import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.remote.parser.CurrencyParser
import com.alokomkar.remote.parser.SupportedCurrenciesParser

interface ICurrencyMapper {
    fun mapToCurrencyRate(currencyParser: CurrencyParser): List<CurrencyRate>
    fun mapToSupportedCurrency(supportedCurrenciesParser: SupportedCurrenciesParser): List<SupportedCurrency>
}