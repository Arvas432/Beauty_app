package com.example.beauty_app.ui

import MakeupItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beauty_app.R
import com.example.beauty_app.databinding.FragmentSearchBinding
import com.example.beauty_app.databinding.FragmentSearchHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchHistoryFragment : Fragment() {
    private var _binding: FragmentSearchHistoryBinding? = null
    protected val binding get() = _binding!!
    private val viewModel by viewModel<SearchHistoryViewModel>()
    private var products = ArrayList<MakeupItem>()
    private lateinit var productListAdapter: SearchHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner){
            renderState(it)
        }
        productListAdapter = SearchHistoryAdapter(products, viewModel)
        binding.searchHistoryRv.adapter = productListAdapter
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.clearButton.setOnClickListener {
            viewModel.clearHistory()
            products.clear()
            productListAdapter.notifyDataSetChanged()
            renderEmpty()
        }
        viewModel.showHistory()
    }
    private fun renderDefault(){
        binding.searchHistoryRv.isVisible = false
        binding.emptyHistoryTv.isVisible = false
        binding.clearButton.isVisible = false

    }
    private fun renderEmpty(){
        binding.searchHistoryRv.isVisible = false
        binding.emptyHistoryTv.isVisible = true
        binding.clearButton.isVisible = false

    }
    private fun renderHistory(history: List<MakeupItem>){
        products.clear()
        products.addAll(history)
        productListAdapter.notifyDataSetChanged()
        binding.emptyHistoryTv.isVisible = false
        binding.searchHistoryRv.isVisible = true
        binding.clearButton.isVisible = true
    }
    private fun renderState(state: SearchHistoryState){
        when(state){
            is SearchHistoryState.Default -> renderDefault()
            is SearchHistoryState.EmptyHistory -> renderEmpty()
            is SearchHistoryState.History -> renderHistory(state.products)
        }
    }
}