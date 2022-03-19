package com.alokomkar.mapper

import com.alokomkar.remote.parser.CurrencyParser
import com.alokomkar.remote.parser.SupportedCurrenciesParser

const val INR = "INR"
const val INR_DESC = "Indian Rupee"
const val INR_VALUE = 1.0

const val USD = "USD"
const val USD_DESC = "United States Dollar"
const val USD_VALUE = 7.0

val emptyCurrencyParser = CurrencyParser(
    timestamp = 0L,
    source = "web",
    quotes = emptyMap()
)

val nonemptyCurrencyParser = CurrencyParser(
    timestamp = 0L,
    source = "web",
    quotes = mutableMapOf<String, Double>().apply {
        put(INR, INR_VALUE)
        put(USD, USD_VALUE)
    }
)

val emptySupportedCurrencyParser = SupportedCurrenciesParser(
    currencies = emptyMap()
)

val nonEmptySupportedCurrencyParser = SupportedCurrenciesParser(
    currencies = mutableMapOf<String, String>().apply {
        put(INR, INR_DESC)
        put(USD, USD_DESC)
    }
)