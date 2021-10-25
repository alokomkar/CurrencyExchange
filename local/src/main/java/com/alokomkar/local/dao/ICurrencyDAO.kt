package com.alokomkar.local.dao

import androidx.room.*
import com.alokomkar.local.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

@Dao
interface ICurrencyDAO {

    @Query("SELECT * FROM currency_rate")
    fun getAllAsFlow(): Flow<List<CurrencyRate>>

    @Query("SELECT * FROM currency_rate")
    fun getAll(): List<CurrencyRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: List<CurrencyRate>)

    @Query("DELETE FROM currency_rate")
    fun deleteAll()

}