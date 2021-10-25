package com.alokomkar.currency.ui

import android.util.Log
import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alokomkar.core.DispatcherProvider
import com.alokomkar.core.Event
import com.alokomkar.core.Result
import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.repository.ICurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: ICurrencyRepository,
    private val dispatcherProvider: DispatcherProvider): ViewModel() {

    private val _uiLiveData: MutableLiveData<UiState> = MutableLiveData()
    val uiLiveData: LiveData<UiState> by this::_uiLiveData

    private var originalCurrencyRates: MutableList<CurrencyRate> = mutableListOf()

    private val _errorStateLiveData: MutableLiveData<Event<ErrorState>> = MutableLiveData()
    val errorStateLiveData: LiveData<Event<ErrorState>> by this::_errorStateLiveData

    private val _inputAmountFlow = MutableStateFlow(Pair("", ""))

    @FlowPreview
    @ExperimentalCoroutinesApi
    val currencyConversionResult: Flow<List<CurrencyRate>> = _inputAmountFlow.debounce(1000).mapLatest {
        return@mapLatest performCurrencyConversion(it)
    }

    @AnyThread
    private suspend fun performCurrencyConversion(amountAndCurrency: Pair<String, String>): List<CurrencyRate>
            = withContext(dispatcherProvider.default) {
        val convertedCurrencyRates: MutableList<CurrencyRate> = mutableListOf()
        val inputAmountString = if(amountAndCurrency.first.isEmpty()) "1" else amountAndCurrency.first
        val amount = inputAmountString.toDouble()
        val selectedCurrency = amountAndCurrency.second
        Log.d("VM", "Currency Conversion : $amountAndCurrency")
        //Conversion implementation :
        //if selectedCurrency is CAD, we are looking for USDCAD
        //for 1 CAD = 0.81 USD
        val selectedCurrencyRate = originalCurrencyRates.first {
            it.currency.endsWith(selectedCurrency)
        }

        for(currencyRate in originalCurrencyRates) {
            //if amount = 2
            val convertedAmount = amount * selectedCurrencyRate.rate * currencyRate.rate //2 * 0.81 USD = 1.62 USD
            val toCurrency = currencyRate.currency.takeLast(3)
            val convertedCurrency = selectedCurrency + toCurrency
            if (selectedCurrency == toCurrency) {
                convertedCurrencyRates.add(
                    CurrencyRate(
                        convertedCurrency,
                        amount
                    )
                )
            } else {
                convertedCurrencyRates.add(
                    CurrencyRate(
                        convertedCurrency,
                        convertedAmount
                    )
                )
            }

        }
        return@withContext convertedCurrencyRates
    }

    fun fetchCurrencyData() {
        _uiLiveData.value = UiState(isLoading = true)
        viewModelScope.launch(dispatcherProvider.io) {
            val currencyRatesFlow = repository.fetchCurrencyRates()
            val supportedCurrenciesFlow = repository.fetchSupportedCurrencies()
            currencyRatesFlow.combine(supportedCurrenciesFlow) {
                currencyRates, supportedCurrencies ->
                var currencyState = UiState(isLoading = false)
                var errorState = ErrorState(error = null)
                when {
                    currencyRates is Result.Success && supportedCurrencies is Result.Success -> {
                        originalCurrencyRates = ArrayList(currencyRates.data)
                        currencyState = UiState(
                            isLoading = false,
                            currencyRates = currencyRates.data,
                            supportedCurrencies = supportedCurrencies.data
                        )
                    }
                    else -> {
                        val exception = when {
                            currencyRates is Result.Error -> {
                                currencyRates.exception
                            }
                            supportedCurrencies is Result.Error -> {
                                supportedCurrencies.exception
                            }
                            else -> {
                                Exception("Unknown Error : fetchCurrencyData")
                            }
                        }
                        errorState = ErrorState(exception)
                    }
                }
                return@combine Pair(currencyState, errorState)
            }.collect {
                viewModelScope.launch(dispatcherProvider.main) {
                    _uiLiveData.value = it.first
                    _errorStateLiveData.value = Event(it.second)
                }
            }
        }
    }

    fun convertCurrency(input: String, selectedCurrency: CharSequence) {
        _inputAmountFlow.value = Pair(input, selectedCurrency.toString())
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    suspend fun observeConversion() = withContext(dispatcherProvider.main) {
        currencyConversionResult.collect {
            var uiState = uiLiveData.value
            uiState = uiState?.copy(
                currencyRates = it
            )
            _uiLiveData.value = uiState
        }
    }


}