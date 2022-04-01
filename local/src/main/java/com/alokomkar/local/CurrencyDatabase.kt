package com.alokomkar.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alokomkar.local.dao.ICurrencyDAO
import com.alokomkar.local.dao.ISupportedCurrencyDAO
import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency

@Database(entities = [CurrencyRate::class, SupportedCurrency::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): ICurrencyDAO
    abstract fun supportedCurrencyDao(): ISupportedCurrencyDAO

}