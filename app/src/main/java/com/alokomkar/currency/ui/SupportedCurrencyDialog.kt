package com.alokomkar.currency.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alokomkar.currency.databinding.FragmentSupportedCurrencyBinding
import com.alokomkar.local.model.SupportedCurrency
import com.alokomkar.currency.ui.adapter.SupportedCurrencyAdapter
import com.alokomkar.currency.ui.adapter.SupportedCurrencyDiffUtilCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SupportedCurrencyDialog: BottomSheetDialogFragment() {

    private var supportedCurrencyItemClick: SupportedCurrencyItemClick? = null
    private lateinit var binding: FragmentSupportedCurrencyBinding
    private val supportedCurrencyAdapter: SupportedCurrencyAdapter by lazy {
        SupportedCurrencyAdapter(SupportedCurrencyDiffUtilCallback) {
            supportedCurrencyItemClick?.onItemClick(it)
            dismiss()
        }
    }
    private val viewModel: CurrencyViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SupportedCurrencyItemClick) {
            supportedCurrencyItemClick = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSupportedCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSupportedCurrency.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = supportedCurrencyAdapter
            viewModel.uiLiveData.observe(viewLifecycleOwner, {
                supportedCurrencyAdapter.submitList(it.supportedCurrencies)
            })
        }
    }

    interface SupportedCurrencyItemClick {
        fun onItemClick(supportedCurrency: SupportedCurrency)
    }

}