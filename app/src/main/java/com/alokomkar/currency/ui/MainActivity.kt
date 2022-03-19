package com.alokomkar.currency.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alokomkar.core.EventObserver
import com.alokomkar.currency.R
import com.alokomkar.currency.databinding.ActivityMainBinding
import com.alokomkar.local.model.CurrencyRate
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.currency.ui.adapter.CurrencyAdapter
import com.alokomkar.currency.ui.adapter.CurrencyDiffUtilCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity: AppCompatActivity(), SupportedCurrencyDialog.SupportedCurrencyItemClick {

    private lateinit var binding: ActivityMainBinding
    private val currencyViewModel: CurrencyViewModel by viewModels()
    private val currencyAdapter by lazy { CurrencyAdapter(CurrencyDiffUtilCallback) }
    private val lazyStartObservation by lazy {
        lifecycleScope.launch {
            currencyViewModel.observeConversion()
        }
    } 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        currencyViewModel.fetchCurrencyData()
        setObservers()
    }

    private fun setupViews() {
        binding.apply {
            rvCurrency.apply {
                layoutManager = GridLayoutManager(this.context, 2)
                adapter = currencyAdapter
            }
            tvSelectedCurrency.setOnClickListener {
                SupportedCurrencyDialog().show(supportFragmentManager, "SupportedCurrencyDialog")
            }
            etCurrencyValue.addTextChangedListener {
                val inputCurrency = it?.toString()
                if(!inputCurrency.isNullOrEmpty()) {
                    currencyViewModel.convertCurrency(
                        inputCurrency,
                        tvSelectedCurrency.text?.trim() ?: getString(R.string.usd)
                    )
                    lazyStartObservation
                }
            }
        }
    }

    private fun setObservers() {
        currencyViewModel.uiLiveData.observe(this) {
            debugLog("Loading : ${it.isLoading}")
            debugLog("State : Currency Rates : ${it.currencyRates}")
            debugLog("State : Supported Currencies : ${it.supportedCurrencies}")
            bindData(it.currencyRates)
        }
        currencyViewModel.errorStateLiveData.observe(this, EventObserver {
            debugLog("Error : ${it.error}")
            handleError(it.error)
        })
    }

    private fun bindData(currencyRates: List<CurrencyRate>) {
        currencyAdapter.submitList(currencyRates)
        binding.apply {
            val isEmpty = currencyRates.isEmpty()
            rvCurrency.isVisible = !isEmpty
            emptyView.isVisible = isEmpty
        }
    }

    private fun handleError(error: Throwable?) {
        error ?: return
        Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    private fun debugLog(log: String) {
        Log.d("MainUI", log)
    }

    override fun onItemClick(supportedCurrency: SupportedCurrency) {
        binding.apply {
            tvSelectedCurrency.text = supportedCurrency.currencyKey
            val input = etCurrencyValue.text?.toString()
            if(input.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, getString(R.string.required), Toast.LENGTH_SHORT).show()
                etCurrencyValue.requestFocus()
                return@apply
            }
            currencyViewModel.convertCurrency(
                etCurrencyValue.text?.toString() ?: "",
                supportedCurrency.currencyKey
            )
        }
    }
}