package com.example.beauty_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beauty_app.App
import com.example.beauty_app.R
import com.example.beauty_app.databinding.FragmentSearchBinding
import com.example.beauty_app.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_startFragment)
        }
        if((requireActivity().applicationContext as App).darkTheme){
            binding.nightModeSwitch.isChecked = true
        }
        val sharedPrefs = requireActivity().getSharedPreferences(
            SHARED_PREFERENCES_KEY,
            AppCompatActivity.MODE_PRIVATE
        )
        binding.nightModeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            (requireActivity().applicationContext as App).switchTheme(isChecked)
            if (isChecked){
                sharedPrefs
                    .edit()
                    .putString(THEME_MODE_KEY, DARK_MODE_VALUE)
                    .apply()
            }
            else{
                sharedPrefs
                    .edit()
                    .putString(THEME_MODE_KEY, LIGHT_MODE_VALUE)
                    .apply()
            }
        }
    }
    companion object{
        const val THEME_MODE_KEY = "key_for_theme_mode"
        const val SHARED_PREFERENCES_KEY = "shared_preferences_key"
        const val DARK_MODE_VALUE = "dark"
        const val LIGHT_MODE_VALUE = "light"
    }
}