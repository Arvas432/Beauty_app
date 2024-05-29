package com.example.beauty_app.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beauty_app.R
import com.example.beauty_app.databinding.ImagesItemViewBinding
import com.example.beauty_app.databinding.SearchListItemBinding
import com.example.beauty_app.domain.search.models.Article
import com.google.gson.Gson

class ArticlesAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ViewHolderImageItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderImageItem {
        val layoutInspector = LayoutInflater.from(parent.context)
        return ViewHolderImageItem (ImagesItemViewBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolderImageItem, position: Int) {
        holder.bind(articles[position])
    }


}