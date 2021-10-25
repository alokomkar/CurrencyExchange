package com.alokomkar.currency.di

import com.alokomkar.core.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @Singleton
    internal fun provideDispatcher(): DispatcherProvider = DispatcherProvider()
}