package com.example.nutrition_app.data.model.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamApiService {
    @GET("nutrition-data")
    suspend fun getNutrientInfo(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("ingr") ingredient: String
    ): Response<NutrientResponse>
}