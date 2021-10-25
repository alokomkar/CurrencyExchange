package com.alokomkar.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supported_currency")
data class SupportedCurrency(
    @PrimaryKey
    val currencyKey: String,
    val currencyValue: String
)