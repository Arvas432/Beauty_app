package com.example.beauty_app.ui

import MakeupItem
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.beauty_app.R
import com.example.beauty_app.databinding.SearchListItemBinding


class ProductViewHolder(private val binding: SearchListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(model: MakeupItem){
        binding.productName.text = model.name
        Glide.with(itemView)
            .load(model.image_link)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.product_image_rounding)))
            .into(binding.productImage)
        binding.brandName.text = model.brand
        if(model.price == 0.0f){
            binding.price.text = "?"
        }else{
            binding.price.text = model.price.toString()
        }
        binding.rating.text = model.rating
    }
}