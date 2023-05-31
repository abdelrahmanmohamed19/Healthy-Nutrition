package com.example.nutrition_app.data.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EdamamApiClient {
    val baseurl = "https://api.edamam.com/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(EdamamApiService::class.java)
}