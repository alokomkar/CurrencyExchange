package com.alokomkar.currency.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alokomkar.currency.databinding.ItemSupportedCurrencyBinding
import com.alokomkar.local.model.SupportedCurrency

@SuppressLint("ClickableViewAccessibility")
class SupportedCurrencyViewHolder(itemView: View, onItemClick: (position: Int)-> Unit): RecyclerView.ViewHolder(itemView) {

    private val binding: ItemSupportedCurrencyBinding = ItemSupportedCurrencyBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            val itemPosition = adapterPosition
            if( itemPosition != RecyclerView.NO_POSITION ) {
                onItemClick.invoke(itemPosition)
            }
        }
    }

    fun bindData(item: SupportedCurrency) {
        binding.apply {
            tvCurrencyKey.text = item.currencyKey
            tvCurrencyFull.text = item.currencyValue
        }
    }

}