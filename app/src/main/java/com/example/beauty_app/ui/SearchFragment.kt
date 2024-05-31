package com.example.beauty_app.ui

import MakeupItem
import SearchState
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beauty_app.R
import com.example.beauty_app.databinding.FragmentSearchBinding
import com.example.beauty_app.domain.search.models.Article
import com.example.beauty_app.domain.search.models.Service
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    protected val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var searchFieldEmpty: Boolean = true
    private var products = ArrayList<MakeupItem>()
    private var services = arrayListOf<Service>(Service("Ботулинотерапия","50 мин", "300-13000 руб", "https://vk.com/wall-211044023_304"), Service("Массаж", "50 мин","1000-5000 руб" , "https://vk.com/wall-211044023_304"))
    private var articles = arrayListOf<Article>(Article("Мифы о\nлазерной\nэпиляции", "https://vk.com/wall-211044023_310"),Article("Подбери\nсебе уход","https://vk.com/wall-211044023_311" ))
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var servicesAdapter: ServicesAdapter
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner){
            renderState(it)
        }
        if (arguments!=null){
            binding.userName.text = requireArguments().getString(LOGIN_VALUE_KEY)
        }
        servicesAdapter = ServicesAdapter(services)
        articlesAdapter = ArticlesAdapter(articles)
        binding.searchEt.setText(viewModel.getSearchData())
        productListAdapter = ProductListAdapter(products, viewModel)
        binding.searchRv.adapter = productListAdapter
        binding.imagesRv.adapter = articlesAdapter
        binding.servicesRv.adapter = servicesAdapter
        val searchFieldTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()){
                    if (!binding.searchEt.hasFocus() || binding.searchEt.text.isNotEmpty()) {
                        setDefaultScreenState()
                    }
                    binding.clearButton.isVisible = false
                }
                else{
                    setDefaultScreenState()
                    searchFieldEmpty = false
                    binding.clearButton.isVisible = true
                    viewModel.setSearchData(s.toString())
                    viewModel.searchDebounce()
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        }
        binding.searchEt.addTextChangedListener(searchFieldTextWatcher)
        binding.clearButton.setOnClickListener {
            products.clear()
            productListAdapter.notifyDataSetChanged()
            requireActivity().currentFocus?.let {
                val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
            binding.searchEt.setText("")
        }

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.immediateSearch()
            }
            false
        }
        binding.searchHistoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchHistoryFragment)
        }
    }
    private fun setNetworkErrorScreenState(){
        setDefaultScreenState()
        binding.notWorkingLayout.isVisible = false
        binding.networkErrorTv.isVisible = true
        binding.emojiErrorIv.isVisible = true
    }
    private fun setLoadingScreenState(){
        setDefaultScreenState()
        binding.notWorkingLayout.isVisible = false
        binding.loadingPb.isVisible = true
    }
    private fun setEmptyResultsScreenState(){
        setDefaultScreenState()
        binding.notWorkingLayout.isVisible = false
        binding.emptyResultsTv.isVisible = true
        binding.emojiIv.isVisible = true

    }
    private fun setDefaultScreenState(){
        binding.notWorkingLayout.isVisible = true
        binding.searchRv.isVisible = false
        binding.loadingPb.isVisible = false
        binding.networkErrorTv.isVisible = false
        binding.emojiErrorIv.isVisible = false
        binding.emptyResultsTv.isVisible = false
        binding.emojiIv.isVisible = false
    }
    private fun setContentScreenState(results: List<MakeupItem>){
        setDefaultScreenState()
        binding.notWorkingLayout.isVisible = false
        products.clear()
        binding.loadingPb.isVisible = false
        products.addAll(results)
        productListAdapter.notifyDataSetChanged()
        binding.searchRv.isVisible = true
    }
    private fun renderState(state: SearchState){
        when(state){
            is SearchState.Default ->{setDefaultScreenState()}
            is SearchState.Loading ->{setLoadingScreenState()}
            is SearchState.NetworkError ->{setNetworkErrorScreenState()}
            is SearchState.EmptyResults ->{setEmptyResultsScreenState()}
            is SearchState.Content ->{setContentScreenState(state.products) }
            else -> {}
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val LOGIN_VALUE_KEY = "LOGIN_VALUE_KEY"
    }
}