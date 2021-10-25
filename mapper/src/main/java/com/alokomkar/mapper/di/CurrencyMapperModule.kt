package com.alokomkar.mapper.di

import com.alokomkar.mapper.CurrencyMapper
import com.alokomkar.mapper.ICurrencyMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CurrencyMapperModule {

    @Binds
    fun bindsCurrencyMapper(currencyMapper: CurrencyMapper): ICurrencyMapper

}