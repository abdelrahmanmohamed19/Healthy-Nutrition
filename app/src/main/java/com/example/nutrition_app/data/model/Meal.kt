package com.example.nutrition_app.data.model

import java.io.Serializable

data class Meal(
    val name: String,
    val calories: Double,
    val totalFat: Double,
    val sodium: Double,
    val vitaminC: Double,
    val vitaminD: Double,
    val calcium: Double,
    val magnesium: Double,
    val potassium: Double,
    val protein: Double,
    val carbohydrate: Double,
    val fiber: Double,
    val sugars: Double,
    val caffeine: Double
) : Serializable