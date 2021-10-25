package com.alokomkar.currency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alokomkar.currency.R
import com.alokomkar.local.model.SupportedCurrency

class SupportedCurrencyAdapter(
    diffUtilCallback: SupportedCurrencyDiffUtilCallback,
    private val onItemClick: (SupportedCurrency) -> Unit
): ListAdapter<SupportedCurrency, SupportedCurrencyViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportedCurrencyViewHolder
            = SupportedCurrencyViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_supported_currency, parent, false)
    ) { position ->
        onItemClick.invoke(getItem(position))
    }

    override fun onBindViewHolder(holder: SupportedCurrencyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}