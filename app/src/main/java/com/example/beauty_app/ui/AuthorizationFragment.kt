package com.example.beauty_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.beauty_app.R
import com.example.beauty_app.databinding.FragmentAuthorizationBinding
import com.example.beauty_app.databinding.FragmentProductInfoBinding
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val authViewModel: AuthorizationViewModel by viewModel()
    private val handler = Handler(Looper.getMainLooper())
    private var isSignIn: Boolean = true
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments!=null){
            isSignIn = requireArguments().getBoolean(LOGIN_KEY)
        }
        when(isSignIn){
            true -> renderSignIn()
            false -> renderSignUp()
        }
        authViewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->

            if(result.contains("Registration Successful")){
                Toast.makeText(requireContext(),
                    getString(R.string.registration_success), Toast.LENGTH_SHORT).show()
                handler.postDelayed({findNavController().navigate(R.id.action_authorizationFragment_to_searchFragment, bundleOf(
                    LOGIN_VALUE_KEY to binding.loginEt.text.toString()))}, DELAY)
            }else{
                Toast.makeText(requireContext(),
                    getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        })

        authViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->

            if(result.contains("Login Successful")){
                Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
                handler.postDelayed({findNavController().navigate(R.id.action_authorizationFragment_to_searchFragment, bundleOf(
                    LOGIN_VALUE_KEY to binding.loginEt.text.toString()))}, DELAY)
            }else{
                Toast.makeText(requireContext(),
                    getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        })
        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.loginButton.setOnClickListener {
            when(isSignIn){
                true -> {
                    val login = binding.loginEt.text.toString()
                    val password = binding.passwordEt.text.toString()
                    authViewModel.login(login, password)
                }
                false -> {
                    val login = binding.loginEt.text.toString()
                    val email = binding.emailEt.text.toString()
                    val password = binding.passwordEt.text.toString()
                    authViewModel.register(login, email, password)
                }
            }
        }
        binding.startButton.setOnClickListener {
            when(isSignIn){
                true -> { renderSignUp(); isSignIn = false}
                false -> {renderSignIn(); isSignIn = true}
            }
        }

    }
    private fun renderSignIn(){
        binding.loginHeader.text = getString(R.string.sign_in)
        binding.emailEt.isVisible = false
        binding.emailTop.isVisible = false
        binding.loginButton.text = getString(R.string.continue_text)
        binding.startButton.isVisible = false
        binding.alreadyHaveAnAccount.isVisible = false
    }
    private fun renderSignUp(){
        binding.loginHeader.text = getString(R.string.sign_up)
        binding.emailEt.isVisible = true
        binding.emailTop.isVisible = true
        binding.loginButton.text = getString(R.string.register)
        binding.startButton.isVisible = true
        binding.alreadyHaveAnAccount.isVisible = true
    }
    companion object {
        const val DELAY = 500L
        const val LOGIN_KEY = "REGISTER_KEY"
        const val LOGIN_VALUE_KEY = "LOGIN_VALUE_KEY"
    }
}