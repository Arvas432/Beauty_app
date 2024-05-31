package com.example.beauty_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beauty_app.R
import com.example.beauty_app.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_authorizationFragment, bundleOf(
                LOGIN_KEY to true
            ))
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_authorizationFragment,bundleOf(
                LOGIN_KEY to false
            ))
        }
    }
    companion object{
        const val LOGIN_KEY = "REGISTER_KEY"
    }
}