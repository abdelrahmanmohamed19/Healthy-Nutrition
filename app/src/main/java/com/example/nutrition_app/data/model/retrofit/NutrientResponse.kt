package com.example.nutrition_app.data.model.retrofit

import com.google.gson.annotations.SerializedName

data class NutrientResponse(
    val calories: Int,
    val totalNutrients: NutrientData,
)

data class NutrientData(
    @SerializedName("CHOCDF")
    val carbohydrates: Nutrient,
    @SerializedName("PROCNT")
    val protein: Nutrient,
    @SerializedName("FAT")
    val fat: Nutrient
)


data class Nutrient(
    val label: String,
    val quantity: Double,
    val unit: String
)


