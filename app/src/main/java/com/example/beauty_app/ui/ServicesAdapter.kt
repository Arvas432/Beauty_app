package com.example.beauty_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beauty_app.databinding.ImagesItemViewBinding
import com.example.beauty_app.databinding.ServiceListItemBinding
import com.example.beauty_app.domain.search.models.Article
import com.example.beauty_app.domain.search.models.Service

class ServicesAdapter(private val services: List<Service>) : RecyclerView.Adapter<ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return ServiceViewHolder (ServiceListItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return services.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }


}