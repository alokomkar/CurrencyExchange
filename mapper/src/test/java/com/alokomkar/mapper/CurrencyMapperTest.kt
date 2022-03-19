package com.alokomkar.mapper

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CurrencyMapperTest {

    private var currencyMapper: CurrencyMapper? = null

    @Before
    fun setUp() {
        currencyMapper = CurrencyMapper()
    }

    @After
    fun tearDown() {
        currencyMapper = null
    }

    @Test
    fun `test for empty currency rate conversion`() {
        currencyMapper?.apply {
            val currencyRateList = mapToCurrencyRate(emptyCurrencyParser)
            assertEquals(currencyRateList.isEmpty(), true)
        }
    }

    @Test
    fun `test Map To Currency Rate Conversion`() {
        currencyMapper?.apply {

            val currencyRateList = mapToCurrencyRate(nonemptyCurrencyParser)
            assertEquals(currencyRateList.isNotEmpty(), true)

            var index = 0
            var currencyRate = currencyRateList[index++]
            assertEquals(currencyRate.currency, INR)
            assertEquals(currencyRate.rate, INR_VALUE, 0.0)

            currencyRate = currencyRateList[index]
            assertEquals(currencyRate.currency, USD)
            assertEquals(currencyRate.rate, USD_VALUE, 0.0)

        }
    }

    @Test
    fun `test Map To Supported Currency - empty yields empty`() {
        currencyMapper?.apply {
            val supportedCurrencies = mapToSupportedCurrency(emptySupportedCurrencyParser)
            assertEquals(supportedCurrencies.isEmpty(), true)
        }
    }

    @Test
    fun `test Map To Supported Currency conversion`() {
        currencyMapper?.apply {
            val supportedCurrencies = mapToSupportedCurrency(nonEmptySupportedCurrencyParser)
            assertEquals(supportedCurrencies.isNotEmpty(), true)

            var index = 0
            var currencyRate = supportedCurrencies[index++]
            assertEquals(currencyRate.currencyKey, INR)
            assertEquals(currencyRate.currencyValue, INR_DESC)

            currencyRate = supportedCurrencies[index]
            assertEquals(currencyRate.currencyKey, USD)
            assertEquals(currencyRate.currencyValue, USD_DESC)

        }
    }
}