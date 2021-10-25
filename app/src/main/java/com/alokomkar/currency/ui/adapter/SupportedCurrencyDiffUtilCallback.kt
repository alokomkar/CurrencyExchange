package com.alokomkar.currency.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alokomkar.local.model.SupportedCurrency

object SupportedCurrencyDiffUtilCallback : DiffUtil.ItemCallback<SupportedCurrency>()  {

    override fun areItemsTheSame(oldItem: SupportedCurrency, newItem: SupportedCurrency): Boolean
            = oldItem.currencyKey == newItem.currencyKey

    override fun areContentsTheSame(oldItem: SupportedCurrency, newItem: SupportedCurrency): Boolean
            = oldItem == newItem
}