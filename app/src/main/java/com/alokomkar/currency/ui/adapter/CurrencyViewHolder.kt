package com.alokomkar.currency.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alokomkar.currency.databinding.ItemCurrencyRateBinding
import com.alokomkar.local.model.CurrencyRate
import java.text.DecimalFormat

class CurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding: ItemCurrencyRateBinding = ItemCurrencyRateBinding.bind(itemView)

    fun bindData(item: CurrencyRate) {
        binding.apply {
            tvCurrencyKey.text = item.currency
            tvCurrencyValue.text = DecimalFormat.getInstance().format(item.rate)
        }
    }

}