package com.alokomkar.remote.parser

import com.alokomkar.remote.base.BaseParser
import com.google.gson.annotations.SerializedName

data class SupportedCurrenciesParser(
    @SerializedName("currencies") val currencies: Map<String, String>
): BaseParser()