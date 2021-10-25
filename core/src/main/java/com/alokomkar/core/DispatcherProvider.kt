package com.alokomkar.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class DispatcherProvider {
    open val main: CoroutineDispatcher by lazy { Dispatchers.Main }
    open val io: CoroutineDispatcher by lazy { Dispatchers.IO }
    open val default: CoroutineDispatcher by lazy { Dispatchers.Default }
}