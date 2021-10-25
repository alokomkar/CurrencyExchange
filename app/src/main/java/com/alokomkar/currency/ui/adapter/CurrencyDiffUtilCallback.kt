package com.alokomkar.currency.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alokomkar.local.model.CurrencyRate

object CurrencyDiffUtilCallback : DiffUtil.ItemCallback<CurrencyRate>()  {
    override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean
        = oldItem.currency == newItem.currency

    override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean
        = oldItem == newItem
}