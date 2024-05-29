package com.example.beauty_app.di

import MakeupApi
import NetworkClient
import RetrofitNetworkClient
import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module{
    val iTunesBaseUrl = "http://makeup-api.herokuapp.com/api/v1/"
    val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
    single<MakeupApi>{
        Retrofit.Builder()
            .baseUrl(iTunesBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MakeupApi::class.java)
    }
    single{
        androidContext().getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, Context.MODE_PRIVATE)
    }
    single{
        Gson()
    }
    single<NetworkClient>{
        RetrofitNetworkClient(get())
    }
}
