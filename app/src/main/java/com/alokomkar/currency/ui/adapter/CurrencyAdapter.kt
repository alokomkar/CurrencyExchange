package com.alokomkar.currency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alokomkar.currency.R
import com.alokomkar.local.model.CurrencyRate

class CurrencyAdapter(diffUtilCallback: CurrencyDiffUtilCallback): ListAdapter<CurrencyRate, CurrencyViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder
        = CurrencyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_currency_rate, parent, false)
        )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}