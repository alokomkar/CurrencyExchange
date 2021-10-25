package com.alokomkar.remote.parser

import com.alokomkar.remote.base.BaseParser
import com.google.gson.annotations.SerializedName

data class CurrencyParser(
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("source") val source: String, //"USD"
    @SerializedName("quotes") val quotes: Map<String, Double>
): BaseParser()