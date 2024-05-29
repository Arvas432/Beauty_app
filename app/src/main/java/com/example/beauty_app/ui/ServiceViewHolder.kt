package com.example.beauty_app.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.beauty_app.R
import com.example.beauty_app.databinding.ImagesItemViewBinding
import com.example.beauty_app.databinding.ServiceListItemBinding
import com.example.beauty_app.domain.search.models.Article
import com.example.beauty_app.domain.search.models.Service
import kotlin.random.Random

class ServiceViewHolder(private val binding: ServiceListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(model: Service){
        binding.title.text = model.title
        binding.timer.text = model.timer
        binding.price.text = model.price
        val random = Random.nextInt(2)
        when(model.title){
            "Ботулинотерапия" -> binding.image.background = itemView.resources.getDrawable(R.drawable.botox)
            "Массаж" -> binding.image.background = itemView.resources.getDrawable(R.drawable.massage)
            else ->  {
                binding.image.background = itemView.resources.getDrawable(R.drawable.massage)
            }
        }
    }
}