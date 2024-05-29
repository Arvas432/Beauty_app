package com.example.beauty_app.ui

import MakeupItem
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beauty_app.R
import com.example.beauty_app.databinding.SearchListItemBinding
import com.google.gson.Gson

class SearchHistoryAdapter(private val products: List<MakeupItem>, private val viewModel: SearchHistoryViewModel) : RecyclerView.Adapter<ProductViewHolder>(){
    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return ProductViewHolder(SearchListItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
        holder.itemView.setOnClickListener{
            if(clickDebounce()){
                viewModel.writeToHistory(products[position])
                holder.itemView.findNavController().navigate(
                    R.id.action_searchHistoryFragment_to_productInfoFragment, bundleOf(
                        PRODUCT_KEY to Gson().toJson(products[position]))
                )
            }
        }
    }
    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
    companion object{
        const val PRODUCT_KEY = "product_key"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}