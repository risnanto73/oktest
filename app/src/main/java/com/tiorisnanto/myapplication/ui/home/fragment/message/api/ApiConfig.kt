package com.tiorisnanto.myapplication.ui.home.fragment.message.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    //https://newsapi.org/v2/top-headlines?country=id&apiKey=85457557600242688da46b5083f618ee
    const val BASE_URL = "https://newsapi.org/v2/"

    fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService() : ApiService{
        return getRetrofit().create(ApiService::class.java)
    }

}