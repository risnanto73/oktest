package com.tiorisnanto.myapplication.ui.home.fragment.message.api

import com.tiorisnanto.myapplication.ui.home.fragment.message.model.ResponseNews
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?country=id&apiKey=85457557600242688da46b5083f618ee")
    fun getNews(): retrofit2.Call<ResponseNews>
}