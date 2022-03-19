package com.alokomkar.repository.di

import com.alokomkar.local.CurrencyPersistenceSource
import com.alokomkar.local.ICurrencyPersistenceSource
import com.alokomkar.local.di.PersistenceModule
import com.alokomkar.mapper.di.CurrencyMapperModule
import com.alokomkar.remote.CurrencyNetworkSource
import com.alokomkar.remote.ICurrencyNetworkSource
import com.alokomkar.remote.di.NetworkModule
import com.alokomkar.repository.CurrencyRepository
import com.alokomkar.repository.ICurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module(includes = [NetworkModule::class, PersistenceModule::class, CurrencyMapperModule::class])
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindCurrencyNetworkSource(
        currencyNetworkSource: CurrencyNetworkSource
    ): ICurrencyNetworkSource

    @Binds
    @Singleton
    fun bindCurrencyPersistenceSource(
        currencyPersistenceSource: CurrencyPersistenceSource
    ): ICurrencyPersistenceSource

    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepository: CurrencyRepository
    ): ICurrencyRepository
}