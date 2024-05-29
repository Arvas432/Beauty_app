package com.example.beauty_app.ui

import MakeupItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.beauty_app.R
import com.example.beauty_app.databinding.FragmentProductInfoBinding
import com.example.beauty_app.databinding.FragmentSearchBinding
import com.google.gson.Gson

class ProductInfoFragment : Fragment() {
    private var _binding: FragmentProductInfoBinding? = null
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val input = Gson().fromJson(requireArguments().getString(PRODUCT_KEY), MakeupItem::class.java)
        binding.productName.text = input.name
        binding.price.text = input.price.toString()
        binding.productDescription.text = input.description
        binding.brandName.text = input.brand
        Glide.with(this)
            .load(input.image_link)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.product_image_rounding)))
            .into(binding.productImage)
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    companion object{
        const val PRODUCT_KEY = "product_key"
    }
}