package com.example.beauty_app.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.beauty_app.R
import com.example.beauty_app.databinding.ImagesItemViewBinding
import com.example.beauty_app.databinding.SearchListItemBinding
import com.example.beauty_app.domain.search.models.Article
import kotlin.random.Random

class ViewHolderImageItem(private val binding: ImagesItemViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(model: Article){
        binding.title.text = model.text
        when(model.text){
            "Мифы о\nлазерной\nэпиляции"-> binding.backgroundImage.background = itemView.resources.getDrawable(R.drawable.kartinochka1)
            "Подбери\nсебе уход" -> binding.backgroundImage.background = itemView.resources.getDrawable(R.drawable.kartinochka_2)
            else -> binding.backgroundImage.background = itemView.resources.getDrawable(R.drawable.kartinochka3)
        }
    }
}