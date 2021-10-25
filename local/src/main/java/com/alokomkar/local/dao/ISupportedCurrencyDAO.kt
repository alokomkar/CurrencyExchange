package com.alokomkar.local.dao

import androidx.room.*
import com.alokomkar.local.model.SupportedCurrency
import kotlinx.coroutines.flow.Flow

@Dao
interface ISupportedCurrencyDAO {

    @Query("SELECT * FROM supported_currency")
    fun getAllAsFlow(): Flow<List<SupportedCurrency>>

    @Query("SELECT * FROM supported_currency")
    fun getAll(): List<SupportedCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: List<SupportedCurrency>)

    @Query("DELETE FROM supported_currency")
    fun deleteAll()

}