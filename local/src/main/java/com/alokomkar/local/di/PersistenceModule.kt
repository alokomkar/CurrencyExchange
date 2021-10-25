package com.alokomkar.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alokomkar.local.CurrencyDatabase
import com.alokomkar.local.R
import com.alokomkar.local.dao.ICurrencyDAO
import com.alokomkar.local.dao.ISupportedCurrencyDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import javax.inject.Singleton

@Suppress("PrivatePropertyName")
@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    private val APP_DATABASE = "currency.db"

    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase): ICurrencyDAO = database.currencyDao()

    @Singleton
    @Provides
    fun provideSupportedCurrencyDao(database: CurrencyDatabase): ISupportedCurrencyDAO = database.supportedCurrencyDao()

    @Singleton
    @Provides
    internal fun provideDatabase(@ApplicationContext context: Context, callback: RoomDatabase.Callback): CurrencyDatabase {
        return Room.databaseBuilder(context, CurrencyDatabase::class.java, APP_DATABASE)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRoomDBCallback(@ApplicationContext context: Context): RoomDatabase.Callback
        = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                insertLocalCurrencyEntries(context, db)
            }
        }

    /**
     * TODO : Check why this happens? API request works fine on Browser
     * Fallback due to the following API issue : http://api.currencylayer.com/list?format=1&access_key=API_KEY
     * Response : 200
     * {
     *    "success": false,
     *    "error": {
     *    "code": 106,
     *    "info": "You have exceeded the maximum rate limitation allowed on your subscription plan.
     *    Please refer to the \"Rate Limits\" section of the API Documentation for details. "
     *       }
     *  }
     * **/
    private fun insertLocalCurrencyEntries(context: Context, db: SupportSQLiteDatabase) {
        val currenciesJsonText = context.resources.openRawResource(R.raw.currencies)
            .bufferedReader().use { it.readText() }
        val currenciesJson = JSONObject(currenciesJsonText).getJSONObject("currencies")
        for(key in currenciesJson.keys()) {
            val values = arrayOf(key, currenciesJson.getString(key))
            db.execSQL("INSERT INTO supported_currency (currencyKey, currencyValue) VALUES (?, ?)", values)
        }
    }

}