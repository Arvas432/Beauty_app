package com.example.beauty_app.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.beauty_app.databinding.ImagesItemViewBinding
import com.example.beauty_app.domain.search.models.Article


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
        holder.itemView.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(articles[position].link)
            holder.itemView.context.startActivity(i)
        }
    }


}