package com.alokomkar.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rate")
data class CurrencyRate(
    @PrimaryKey
    val currency: String,
    var rate: Double
)