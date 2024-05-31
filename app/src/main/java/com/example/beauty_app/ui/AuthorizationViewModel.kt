package com.example.beauty_app.ui

import LoginRecieveRemote
import LoginResponseRemote
import RegisterRecieveRemote
import RegisterResponseRemote
import UserRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationViewModel(private val userRepository: UserRepository):ViewModel() {
    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> get() = _registerResult

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    fun register(username: String, email: String, password: String) {
        val registerData = RegisterRecieveRemote(username, email, password)
        userRepository.register(registerData).enqueue(object : Callback<RegisterResponseRemote> {
            override fun onResponse(call: Call<RegisterResponseRemote>, response: Response<RegisterResponseRemote>) {
                if (response.isSuccessful) {
                    _registerResult.postValue("Registration Successful: Token ${response.body()?.token}")
                } else {
                    _registerResult.postValue("Registration Failed")
                }
            }

            override fun onFailure(call: Call<RegisterResponseRemote>, t: Throwable) {
                _registerResult.postValue("Error: ${t.message}")
            }
        })
    }

    fun login(username: String, password: String) {
        val loginData = LoginRecieveRemote(username, password)
        userRepository.login(loginData).enqueue(object : Callback<LoginResponseRemote> {
            override fun onResponse(call: Call<LoginResponseRemote>, response: Response<LoginResponseRemote>) {
                if (response.isSuccessful) {
                    _loginResult.postValue("Login Successful: Token ${response.body()?.token}")
                } else {
                    _loginResult.postValue("Login Failed")
                }
            }

            override fun onFailure(call: Call<LoginResponseRemote>, t: Throwable) {
                _loginResult.postValue("Error: ${t.message}")
            }
        })
    }
}